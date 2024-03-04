package com.project.tutor.warpper;

import com.project.tutor.request.FeedBackRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestWarpper {
    private FeedBackRequest request;
    private List<Map<String , Integer>> listFeedbacks;
}
