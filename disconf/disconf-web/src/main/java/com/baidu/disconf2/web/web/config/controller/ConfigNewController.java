package com.baidu.disconf2.web.web.config.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.disconf2.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf2.web.service.config.form.ConfNewForm;
import com.baidu.disconf2.web.service.config.service.ConfigMgr;
import com.baidu.disconf2.web.web.config.validator.ConfigValidator;
import com.baidu.disconf2.web.web.config.validator.FileUploadValidator;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.exception.FileUploadException;
import com.baidu.dsp.common.vo.JsonObjectBase;

/**
 * 专用于配置新建
 * 
 * @author liaoqiqi
 * @version 2014-6-24
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/config")
public class ConfigNewController extends BaseController {

    protected static final Logger LOG = LoggerFactory
            .getLogger(ConfigUpdateController.class);

    @Autowired
    private ConfigMgr configMgr;

    @Autowired
    private ConfigValidator configValidator;

    @Autowired
    private FileUploadValidator fileUploadValidator;

    /**
     * 配置项的新建
     * 
     * @param confListForm
     * @return
     */
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase newItem(@Valid ConfNewForm confNewForm) {

        // 业务校验
        configValidator.validateNewItem(confNewForm, DisConfigTypeEnum.ITEM);

        //
        // 更新, 并写入数据库
        //
        configMgr.newConfig(confNewForm, DisConfigTypeEnum.ITEM);

        return buildSuccess("创建成功");
    }

    /**
     * 配置文件的新建
     * 
     * @param desc
     * @param file
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public JsonObjectBase updateFile(@PathVariable long configId,
            @RequestParam("myfilerar") MultipartFile file) {

        //
        // 校验
        //
        int fileSize = 1024 * 1024 * 4;
        String[] allowExtName = { ".properties" };
        fileUploadValidator.validateFile(file, fileSize, allowExtName);

        //
        // 更新
        //
        try {

            String str = new String(file.getBytes(), "UTF-8");
            LOG.info("receive file: " + str);

            configMgr.updateItemValue(configId, str);
            LOG.info("update " + configId + " ok");

        } catch (Exception e) {

            LOG.error(e.toString());
            throw new FileUploadException("upload file error", e);
        }

        return buildSuccess("ok");
    }
}