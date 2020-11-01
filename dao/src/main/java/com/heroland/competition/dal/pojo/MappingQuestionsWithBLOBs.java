package com.heroland.competition.dal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="com.heroland.competition.dal.pojo.MappingQuestionsWithBLOBs")
public class MappingQuestionsWithBLOBs extends MappingQuestions implements Serializable {
    /**
     * 试题-题干
     */
    @ApiModelProperty(value="title试题-题干")
    private String title;

    /**
     * 选项A
     */
    @ApiModelProperty(value="optionA选项A")
    private String optionA;

    /**
     * 选项B
     */
    @ApiModelProperty(value="optionB选项B")
    private String optionB;

    /**
     * 选项C
     */
    @ApiModelProperty(value="optionC选项C")
    private String optionC;

    /**
     * 选项D
     */
    @ApiModelProperty(value="optionD选项D")
    private String optionD;

    /**
     * 选项E
     */
    @ApiModelProperty(value="optionE选项E")
    private String optionE;

    /**
     * 
     */
    @ApiModelProperty(value="answer1")
    private String answer1;

    /**
     * 非标准格式答案或含部分过程说明的答案
     */
    @ApiModelProperty(value="answer2非标准格式答案或含部分过程说明的答案")
    private String answer2;

    /**
     * 试题解析
     */
    @ApiModelProperty(value="parse试题解析")
    private String parse;

    /**
     * mapping_questions
     */
    private static final long serialVersionUID = 1L;

    /**
     * 试题-题干
     * @return title 试题-题干
     */
    public String getTitle() {
        return title;
    }

    /**
     * 试题-题干
     * @param title 试题-题干
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 选项A
     * @return option_a 选项A
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * 选项A
     * @param optionA 选项A
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA == null ? null : optionA.trim();
    }

    /**
     * 选项B
     * @return option_b 选项B
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * 选项B
     * @param optionB 选项B
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB == null ? null : optionB.trim();
    }

    /**
     * 选项C
     * @return option_c 选项C
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * 选项C
     * @param optionC 选项C
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC == null ? null : optionC.trim();
    }

    /**
     * 选项D
     * @return option_d 选项D
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * 选项D
     * @param optionD 选项D
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD == null ? null : optionD.trim();
    }

    /**
     * 选项E
     * @return option_e 选项E
     */
    public String getOptionE() {
        return optionE;
    }

    /**
     * 选项E
     * @param optionE 选项E
     */
    public void setOptionE(String optionE) {
        this.optionE = optionE == null ? null : optionE.trim();
    }

    /**
     * 
     * @return answer1 
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * 
     * @param answer1 
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1 == null ? null : answer1.trim();
    }

    /**
     * 非标准格式答案或含部分过程说明的答案
     * @return answer2 非标准格式答案或含部分过程说明的答案
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * 非标准格式答案或含部分过程说明的答案
     * @param answer2 非标准格式答案或含部分过程说明的答案
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2 == null ? null : answer2.trim();
    }

    /**
     * 试题解析
     * @return parse 试题解析
     */
    public String getParse() {
        return parse;
    }

    /**
     * 试题解析
     * @param parse 试题解析
     */
    public void setParse(String parse) {
        this.parse = parse == null ? null : parse.trim();
    }
}