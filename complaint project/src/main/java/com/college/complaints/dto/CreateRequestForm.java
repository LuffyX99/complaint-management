package com.college.complaints.dto;

import com.college.complaints.model.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateRequestForm {

    @NotNull
    private RequestType type = RequestType.COMPLAINT;

    @NotBlank
    @Size(max = 100)
    private String category;

    @NotBlank
    @Size(max = 140)
    private String title;

    @NotBlank
    @Size(max = 1200)
    private String description;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
