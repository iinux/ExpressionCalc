package com.mycompany.helloworld.springboot.apiversion;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Data
@Slf4j
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    private String version;

    public final String get() {
        return this.version;
    }

    public ApiVersionCondition(String version) {
        if(version == null) {
            this.version = "1.0.0.0";
        } else if(!version.matches("[0-9]+(\\.[0-9]+)*")) {
            this.version = "1.0.0.0";
        } else {
            this.version = version;
        }
    }

    public int compareTo(ApiVersionCondition that) {
        if(that == null) {
            return 1;
        }
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if(thisPart < thatPart) {
                return -1;
            } else if(thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        if(this == that) {
            return true;
        }
        if(that == null) {
            return false;
        }
        if(this.getClass() != that.getClass()) {
            return false;
        }
        return compareTo((ApiVersionCondition) that) == 0;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        return apiVersionCondition;
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        String appVersion = httpServletRequest.getHeader("app-version");
        if (Strings.isEmpty(appVersion)) {
            return this;
        }

        ApiVersionCondition appVersionObj = new ApiVersionCondition(appVersion);
        if (compareTo(appVersionObj) <= 0) {
            return this;
        }

        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        return -1 * compareTo(apiVersionCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }
}
