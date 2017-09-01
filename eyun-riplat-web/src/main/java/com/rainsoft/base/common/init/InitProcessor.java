package com.rainsoft.base.common.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.riplat.web.mgrparam.model.Platform;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;

public class InitProcessor implements ApplicationListener<ContextRefreshedEvent> {
    
    private static final Logger logger = LoggerFactory.getLogger(InitProcessor.class);
    
    @Resource
    private IPlatformService platformServiceImpl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            //initPlatformIdReadFile();
            initPlatformIdFromDB();
        }
    }
    
    /**
     * 以查询DB的方式加载其他平台的PlatformId到Map中
     */
	public void initPlatformIdFromDB(){
        
        List<Platform> platformList = platformServiceImpl.findListBy(new Platform());
        for(Platform platform : platformList){
            if(StringUtil.isNotEmpty(platform.getActiveable())){//排除未生成Key的平台信息
            	/**
            	 * add 增加初始化平台信息，
            	 * bigin 20160510 by huxiaolong
            	 */
                Map<String, String> platMap = new HashMap<String, String>();
                platMap.put("activeableName", platform.getActiveableName());
                platMap.put("platformTypeName", platform.getPlatformTypeName());
                platMap.put("activeable", platform.getActiveableName());
                platMap.put("platformType", platform.getActiveableName());
                platMap.put("platformId", platform.getActiveableName());
                platMap.put("platformName", platform.getActiveableName());
                platMap.put("platformIP", platform.getPlatformIP());
                Constants.PLATFORM_DETAIL_MAP.put(platform.getPlatformId(),platMap);
                /**
                 * end 20160510 by huxiaolong
                 */
                Constants.PLATFORM_MAP.put(platform.getPlatformId(), platform.getActiveable());
            }
        }   
    }

    /**
     * bean加载完成后，以读取文件的方式加载其他平台的platFormId到Map中
     */
    public void initPlatformIdReadFile() {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("basePath " + basePath);
        // 文件所在目录及文件名：
        String filePath = SystemConfigUtil.getValue("PLATFORMID_PATH");
        String fileName = SystemConfigUtil.getValue("PLATFORMID_FILENAME");

        // 自动创建文件目录
        File fileDir = new File(basePath + filePath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // 自动创建文件
        File keyFile = new File(basePath + filePath + File.separator + fileName);
        if (!keyFile.exists()) {
            try {
                keyFile.createNewFile();
            } catch (IOException e) {
                logger.warn("新建文件失败");
                e.printStackTrace();
            }
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = null;
            fis = new FileInputStream(keyFile);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
                                         // InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                String[] mapStrings = str.split(",");
                if(mapStrings.length==2&&StringUtil.isNotEmpty(mapStrings[0])&&StringUtil.isNotEmpty(mapStrings[1])&&(!Constants.PLATFORM_MAP.containsKey(mapStrings[0]))){//判断Key、value是否都有值且不包含该值
                    Constants.PLATFORM_MAP.put(mapStrings[0], mapStrings[1]);
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("找不到指定文件");
        } catch (IOException e) {
            logger.warn("读取文件失败");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                logger.warn("关闭流失败");
                e.printStackTrace();
            }
        }
    }
}
