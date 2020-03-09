package com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class DocumentNotFoundException extends AbstractThrowableProblem {
    private static final long serialVersionUID=1L;
    public DocumentNotFoundException(){
        super(null,"Document Not Found", Status.NOT_FOUND);
    }
}
