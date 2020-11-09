package modules.zhihu;

import dto.QuestionResultDto;

import java.util.List;

public interface IQuestion {
    List<QuestionResultDto> getQuestion();
}
