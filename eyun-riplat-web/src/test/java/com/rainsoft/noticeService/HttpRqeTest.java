/*package com.rainsoft.noticeService;


import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rainsoft.riplat.web.mgrparam.model.Platform;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import com.rainsoft.riplat.web.notice.service.INoticeService;

*//**
 * Created by Sun on 2016/4/13.
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改
 * 我们这最棘手的代码的编程骑士。你，我们的救世主，人中之龙，
 * 我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己。
 * 永远不要哭啼，永远不要说再见。永远不要说谎来伤害自己。
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring*.xml")
public class HttpRqeTest {
	
	@Resource
    private IPlatformService platformServiceImpl;
    
    @Resource
    private INoticeService noticeServiceImpl;
    
    @Test
    public void invoking(){
    	List<Platform> list =platformServiceImpl.getPlatformIdList();
    	String id = getStrRandom(8,list);
    	System.out.println("getStrRandom:"+id);
    }
    
    
    public String getStrRandom(int length,List<Platform> list) {  
        
        String val = "";  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        Boolean check = true;
        while(check){
        	for(int i = 0; i < length; i++) {  
        		
        		String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
        		//输出字母还是数字  
        		if( "char".equalsIgnoreCase(charOrNum) ) {  
        			//输出是大写字母还是小写字母  
        			int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
        			val += (char)(random.nextInt(26) + temp);  
        		} else if( "num".equalsIgnoreCase(charOrNum) ) {  
        			val += String.valueOf(random.nextInt(10));
        		}  
        	}
        	val = val.toLowerCase();
        	int i = 0;
        	for(Platform plat : list){
    			if(val.equals(plat.getPlatformId())){
    				i++;
    				break;
    			}
    			System.out.println("list:"+plat.getPlatformId());
    		}
        	if(i == 0){
        		check = false;  
        	}
        }
        return val;
    }
}
*/