package com.cafe.crm.configs.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("balance-info.mail")
public class BalanceInfoProperties {

    private Deduction deduction;
    private Refill refill;

    public Deduction getDeduction() {
        return deduction;
    }

    public void setDeduction(Deduction deduction) {
        this.deduction = deduction;
    }

    public Refill getRefill() {
        return refill;
    }

    public void setRefill(Refill refill) {
        this.refill = refill;
    }

    public static class Deduction {
        private String view;
        private String subject;

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    public static class Refill {
        private String view;
        private String subject;

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }
}
