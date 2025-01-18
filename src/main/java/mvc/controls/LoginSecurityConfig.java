package mvc.controls;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig {

    @Bean //MVC向けコンフィグ
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/mvc/**");
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/mvc/login").permitAll() //ルートは全ユーザーがアクセス可能。
                .anyRequest().authenticated()); //その他のURLは認証が必要
        http.formLogin((formLogin) -> formLogin
                .loginPage("/mvc/login")
                .defaultSuccessUrl("/mvc/read") //ログイン成功時のアクセス先html。UserDetailsServiceメソッドを使う場合はいらない。
                .permitAll());
        http.csrf((csrf) -> csrf.disable()); //csrf対策無効。検索のPostで403を返すので取り合えず。
        http.logout((logout) -> logout.permitAll());

        return http.build();
    }



    //こちらは、ログイン画面から入力された平文パスワードをBCryptに自動変換するメソッド。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    //データベースのusersとauthoritiesテーブルと照合する
    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);

        //ロード時にデータベースへユーザー作成(テーブルへinsert)も行う
        /*UserDetails user = User.withUsername("user")
                .password(new BCryptPasswordEncoder().encode("pass2user"))
                .roles("USER")
                .build();
        users.createUser(user);*/

        return users;
    }

    //ロード時にインメモリーにテストユーザー情報を保存しておき照合する方法(データベースを使わないサンプル)。パスワードはBCryptに変換して保存。
    //@Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); //先頭に{bcrypt}と付くので注意
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        UserDetails user = User.withUsername("user")
                //.passwordEncoder(encoder::encode)
                //.password(enc.encode("pass2user"))
                .password(encoder.encode("pass2user").replace("{bcrypt}", ""))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .passwordEncoder(encoder::encode)
                .password("passadmin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}