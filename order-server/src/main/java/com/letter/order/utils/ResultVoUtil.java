package com.letter.order.utils;

import com.letter.order.vo.ResultVO;

public class ResultVoUtil {
    public static ResultVO seccuss(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(o);
        return resultVO;
    }
}
