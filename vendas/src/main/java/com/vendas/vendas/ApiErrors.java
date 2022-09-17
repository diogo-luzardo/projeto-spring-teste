package com.vendas.vendas;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ApiErrors {

    private List<String> errors;

    public ApiErrors(String erro) {
        this.errors = Arrays.asList(erro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiErrors apiErrors = (ApiErrors) o;

        return Objects.equals(errors, apiErrors.errors);
    }

    @Override
    public int hashCode() {
        return errors != null ? errors.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ApiErrors{" +
                "errors=" + errors +
                '}';
    }
}
