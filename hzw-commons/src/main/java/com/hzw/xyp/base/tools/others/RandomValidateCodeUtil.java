package com.hzw.xyp.base.tools.others;

import com.hzw.xyp.base.constant.SessionManage;
import com.hzw.xyp.base.exception.BusinessException;
import com.hzw.xyp.base.exception.ResponseCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 随机生成验证码图片
 */
public class RandomValidateCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);

    private int invalidTime = 120; // 失效时间
    private int num = 4;// 随机产生字符数量
    private static char[] VERIFY_CODE_OPTIONS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 获取并打印验证码
     */
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.生成随机的验证码文本
        char[] code = new char[num];
        for (int i = 0; i < code.length; i++){
            int index = (int)(Math.random() * 100) % VERIFY_CODE_OPTIONS.length;
            code[i] = VERIFY_CODE_OPTIONS[index];
        }

        // 2.存进session中
        String verifyCode = String.valueOf(code);
        HttpSession session = request.getSession();
        session.removeAttribute(SessionManage.VALIDATA_CODE);
        session.setAttribute(SessionManage.VALIDATA_CODE, verifyCode);
        //session.setMaxInactiveInterval(invalidTime); //设置session失效时间
        logger.info("生成验证码：" + verifyCode);
        logger.info("生成验证码sessionId：" + session.getId());

        // 打印输出验证码
        this.printVerifyCode(code, response);
    }

    /**
     * 打印输出验证码
     */
    private void printVerifyCode(char[] value, HttpServletResponse response) throws Exception{
        try {
            ImageIO.write(this.createVerifyCode(value), "PNG", response.getOutputStream());
		} catch (Exception e) {
            logger.error("生成验证码错误", e);
            throw new BusinessException(ResponseCodeEnum.Verify_GET_FAIL);
		}
    }

    /**
     * 生成验证码图片
     */
    public BufferedImage createVerifyCode(char[] value){
        int width = 20 * value.length;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        g.setColor(getRadomColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Arial", Font.BOLD, 24));

        for (int i = 0; i < 155; i++){
            g.setColor(getRadomColor(120,200));
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            int x1 = x + 20 - (int)(Math.random() * 40);
            int y1 = y + 20 - (int)(Math.random() * 40);
            g.drawLine(x, y, x1, y1);
        }
        // 取随机产生的认证码(4位数字)
        for (int i = 0; i < value.length; i++){
            // 将认证码显示到图象中
            g.setColor(getRadomColor());//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            int x = 10 + 13 * i + (int)(Math.random() * 5);
            int y = 20 + (int)(Math.random() * 5);
            g.drawString(String.valueOf(value[i]), x, y);
        }
        // 图象生效
        g.dispose();
        return image;
    }

    //<4.1>生成验证码图片
    private Color getRadomColor(int min, int max){//给定范围获得随机颜色
        int r = min + (int)(Math.random() * (max - min));
        int g = min + (int)(Math.random() * (max - min));
        int b = min + (int)(Math.random() * (max - min));
        return new Color(r, g, b);
    }

    //<4.2>给定范围获得随机颜色，基色为红、绿、蓝 三个纯度高的颜色，在此基础上进行小范围调整
    private Color getRadomColor(){
        int[][] rgps = new int[][]{{255,0,0}, {0, 128, 0}, {0, 0, 255}};
        int index = (int)(Math.random() * 10) % rgps.length;
        int[] rgp = rgps[index];

        return new Color(getRandomRgp(rgp[0]), rgp[1], rgp[2]);
    }
    //<4.2.1>
    private int getRandomRgp(int value){
        if(value >= 235){
            return value - (int)(20 * Math.random());
        }else if(value <= 20){
            return value + (int)(20 * Math.random());
        }else{
            return value + 20 - (int)(40 * Math.random());
        }
    }
}
