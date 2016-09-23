package com.luoo.mywork.modules.portal.web;

import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.portal.entity.PortalInfo;
import com.luoo.mywork.modules.portal.entity.PortalItem;
import com.luoo.mywork.modules.portal.entity.PortalRef;
import com.luoo.mywork.modules.portal.entity.PortalWidget;
import com.luoo.mywork.modules.portal.service.PortalService;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luoo
 * @create 2016-09-23 15:54
 */

@Controller
@RequestMapping(value = "${adminPath}/portal")
public class PortalController extends BaseController {
    @Autowired
    private PortalService portalService;

    @RequestMapping("index")
    public String index(Model model) {

        String userId = UserUtils.getUser().getId();
        PortalRef portalRef = portalService.createOrGetPortalRef(userId);

        if (portalRef == null) {
            return "portal/index";
        }

        PortalInfo portalInfo = portalRef.getPortalInfo();

        List<Integer> columnIndexes = portalItemManager
                .find("select distinct columnIndex from PortalItem where portalInfo=? order by columnIndex",
                        portalInfo);
        logger.debug("columnIndexes : {}", columnIndexes);

        if (!columnIndexes.contains(Integer.valueOf(1))) {
            columnIndexes.add(Integer.valueOf(1));
        }

        if (!columnIndexes.contains(Integer.valueOf(2))) {
            columnIndexes.add(Integer.valueOf(2));
        }

        if (!columnIndexes.contains(Integer.valueOf(3))) {
            columnIndexes.add(Integer.valueOf(3));
        }

        Collections.sort(columnIndexes);

        Map<Integer, List<PortalItem>> map = new LinkedHashMap<Integer, List<PortalItem>>();

        for (Integer columnIndex : columnIndexes) {
            List<PortalItem> portalItems = portalItemManager
                    .find("from PortalItem where portalInfo=? and columnIndex=? order by rowIndex",
                            portalInfo, columnIndex);
            map.put(columnIndex, portalItems);
        }

        model.addAttribute("map", map);

        List<PortalWidget> portalWidgets = portalWidgetManager.getAll();
        model.addAttribute("portalWidgets", portalWidgets);

        return "portal/index";

    }

}
