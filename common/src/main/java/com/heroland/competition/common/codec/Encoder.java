package com.heroland.competition.common.codec;

/**
 * @author: kui.zhouk
 * @date: 2019-04-10
 */
public interface Encoder {


        Object encode(Object source) throws EncoderException;

}
