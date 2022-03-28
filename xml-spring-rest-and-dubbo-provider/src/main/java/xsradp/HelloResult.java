package xsradp;

import java.time.LocalDateTime;

/**
 * @Author ISJINHAO
 * @Date 2022/3/26 17:30
 */
public class HelloResult {

    private String result;

    private LocalDateTime dt;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "HelloResult{" +
                "result='" + result + '\'' +
                ", dt=" + dt +
                '}';
    }

}
