package mvc.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mvc.models.appservices.ShohinAppService;
import mvc.models.appservices.dtos.ShohinDto;
import mvc.models.domainobjects.shohinvalueobjects.ShohinCode;
import mvc.models.domainobjects.shohinvalueobjects.UniqueId;

@Controller
public class ShohinController {

    @Autowired
    ShohinAppService service;

    @RequestMapping(value = "/mvc/read", method = RequestMethod.GET)
    public ModelAndView readAll(ModelAndView mandv) {
        mandv.addObject("btitle", "商品一覧表(全件)");
        List<ShohinDto> data = service.getAllShohinList();
        mandv.addObject("data", data);
        mandv.setViewName("read");
        return mandv;
    }

    @RequestMapping(value = "/mvc/read", method = RequestMethod.POST)
    public ModelAndView readByShohinCode(@RequestParam("find")Integer find, ModelAndView mandv) {
        mandv.addObject("btitle", "商品一覧(抽出)");
        mandv.addObject("value", find);
        ShohinDto data = service.findByShohinCode(new ShohinCode(find));
        mandv.addObject("data", data);
        mandv.setViewName("read");
        return mandv;
    }

    @RequestMapping(value = "/mvc/create", method = RequestMethod.POST)
    public String add(@ModelAttribute ShohinDto shohin) {
        service.registerShohin(String.valueOf(shohin.getShohinCode()), shohin.getShohinName(), shohin.getRemarks());
        return "redirect:/mvc/read";
    }

    @RequestMapping(value = "/mvc/editc/{uniqueId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("uniqueId") String uniqueId, ModelAndView mandv) {
        mandv.addObject("btitle", "商品編集");
        ShohinDto data = service.findByUniqueId(new UniqueId(uniqueId));
        mandv.addObject("data", data);
        mandv.setViewName("edit");
        return mandv;
    }

    @RequestMapping(value = "/mvc/change/{uniqueId}", method = RequestMethod.POST)
    public String change(@PathVariable("uniqueId") String uniqueId, ShohinDto shohin) {
        service.editShohin(uniqueId, String.valueOf(shohin.getShohinCode()), shohin.getShohinName(), shohin.getRemarks());
        return "redirect:/mvc/read";
    }

    @RequestMapping(value = "/mvc/erase/{uniqueId}", method = RequestMethod.POST)
    public String erase(@PathVariable("uniqueId") String uniqueId) {
        service.removeShohin(uniqueId);
        return "redirect:/mvc/read";
    }
}