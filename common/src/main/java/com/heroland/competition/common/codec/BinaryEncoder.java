package com.heroland.competition.common.codec;

/**
 * @author: kui.zhouk
 * @date: 2019-04-10
 */
public interface BinaryEncoder {


    byte[] encode(byte[] source) throws EncoderException;

}
