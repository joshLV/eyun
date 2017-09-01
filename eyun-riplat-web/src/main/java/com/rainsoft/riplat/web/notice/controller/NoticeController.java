package com.rainsoft.riplat.web.notice.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.service.INoticeReplyService;
import com.rainsoft.riplat.web.notice.service.INoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController extends SpringBaseController<Notice, String> {

    @Resource
    private INoticeService noticeServiceImpl;
    
    @Resource
    private IPlatformService platformServiceImpl;
    
    @Resource
    private INoticeReplyService noticeReplyServiceImpl;

    @Override
    protected IMybatisBasePersitenceService<Notice, String> getBaseService() {
        return noticeServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/notice/";
    }
    
    /**
     * 加载组信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadGroupData", method = RequestMethod.POST)
    public void loadGroupData(@ModelAttribute("params") String params) {
        
    }

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> map) {
        List<Notice> list = noticeServiceImpl.findListByMap(map);
        return getPrefix() + "index";
    }
    
    /**
     * 通知列表页
     */
    @RequestMapping("/toList")
    public String toList(Model model) {
        String item = SpringMvcUtil.getParameter("item");
        model.addAttribute("item", item);
        return super.toList(model);
    }

    /**
     * 查看通知的那个页面
     * @param notice
     */
    @ResponseBody
    @RequestMapping("getNoticeById")
    public void getNoticeById(Notice notice) {
        try {
            super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "成功", noticeServiceImpl.findById(notice.getId().toString())));
        } catch (Exception e) {
            e.printStackTrace();
            super.responseAjaxData(new Result(Constants.RETURN_ERROR, "失败", null));
        }
    }

    /**
     * 通知列表页面直接发送
     * @param id
     */
    @ResponseBody
    @RequestMapping("/send")
    public void send(String id) {
        if (StringUtil.isEmpty(id)) {
            super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "id为空", null));
            return;
        }
        try {
            noticeServiceImpl.saveSendNotice(id);
            super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "发送成功", null));
        } catch (Exception e) {
            e.printStackTrace();
            super.responseAjaxData(new Result(Constants.RETURN_ERROR, "发送失败", null));
        }
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public void saveOrUpdate(Notice notice) {
        try {
            String userId = SpringMvcUtil.getUserId().toString(); //将这块放在外面
            noticeServiceImpl.saveOrUpdate(notice,userId);
            super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "保存成功", null));
        } catch (Exception e) {
            e.printStackTrace();
            super.responseAjaxData(new Result(Constants.RETURN_ERROR, "保存失败", null));
        }
    }
    
    /**
     * 通知消息详情
     * WEB端跳转
     * 
     * @param recordId  
     * @param edaId
     * @param model
     * @return
     */
    @RequestMapping(value = "/noticeDetail")
    public String noticeDetail(@ModelAttribute("recordId") String recordId, Model model) {
        if (StringUtil.isNotEmpty(recordId)) {// 判断必填参数是否都有值
            Notice4App notice = noticeServiceImpl.noticeDetail(recordId);
            if (StringUtil.isNotEmpty(recordId)) {
                ReplyHistory replyHistory = new ReplyHistory();
                replyHistory.setNoticeId(recordId);
                List<ReplyHistory> rhList = noticeReplyServiceImpl.findListBy(replyHistory, null, null);
                model.addAttribute("notice", notice);
                model.addAttribute("userId", SpringMvcUtil.getUserId());
                model.addAttribute("rhList", rhList);
                model.addAttribute("userType", SpringMvcUtil.getPlatformId());
                model.addAttribute("isApp","N");
            }
        }
        return getPrefix() + "/noticeDetail";
    }

    /**
     * 跳转上传页面
     * @return
     */
    @RequestMapping("/upload")
    public String upload() {
        return getPrefix() + "upload";
    }

    /**
     * 接收其他平台上传的文件
     * @param model
     * @param file
     * @return
     */
    @RequestMapping("/recieveFile")
    public String recieveFile(Model model, @RequestParam(value = "file", required = false) CommonsMultipartFile file) {
        String filepath = noticeServiceImpl.storeFile(file).replaceAll("\\\\", "/");
        model.addAttribute("filepath", filepath);
        model.addAttribute("filename", file.getOriginalFilename());
        return getPrefix() + "upload";
    }

}
