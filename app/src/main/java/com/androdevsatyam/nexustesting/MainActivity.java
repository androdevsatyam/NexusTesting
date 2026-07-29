package com.androdevsatyam.nexustesting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androdevsatyam.nexus.Nexus;
import com.androdevsatyam.nexus.NexusRequest;
import com.androdevsatyam.nexus.NexusResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final String TAG = "NexusTesting";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView output = new TextView(this);
        output.setTextSize(14f);
        int padding = dp(16);
        output.setPadding(padding, 100, padding, padding);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(output);
        setContentView(scrollView);

        output.setText(runNexusChecks());
    }

    private String runNexusChecks() {
        StringBuilder builder = new StringBuilder();
        Nexus nexus = new Nexus();
        nexus.initialize(getApplicationContext());

        builder.append("Nexus Runtime Test\n");
        builder.append("Version: ").append(nexus.version()).append("\n\n");

        //Surrender 338028, Loan 293950, PaidUp 333333
        runCase(builder, nexus, "814 New Endowment", NexusRequest.builder()
                .planNo(814)
                .age(20)
                .policyTerm(18)
                .premiumPayingTerm(18)
                .sumAssured(500000)
                .gender(NexusRequest.Gender.MALE)
                .paymentMode(NexusRequest.PaymentMode.YEARLY)
                .commencementDate(date("08/03/2015"))
                .firstPremiumDate(date("08/03/2027"))
                .calculationDate(date("29/07/2026"))
                .build());

        //Surrender 218964, Loan 188741, PaidUp 200000
        runCase(builder, nexus, "820 Single Premium Endowment", NexusRequest.builder()
                .planNo(820)
                .age(20)
                .policyTerm(20)
                .premiumPayingTerm(15)
                .sumAssured(500000)
                .gender(NexusRequest.Gender.MALE)
                .paymentMode(NexusRequest.PaymentMode.YEARLY)
                .commencementDate(date("08/03/2015"))
                .firstPremiumDate(date("08/03/2027"))
                .calculationDate(date("29/07/2026"))
                .build());

//        runCase(builder, nexus, "760 Bima Jyoti", NexusRequest.builder()
//                .planNo(760)
//                .age(25)
//                .policyTerm(15)
//                .premiumPayingTerm(10)
//                .sumAssured(500000)
//                .gender(NexusRequest.Gender.MALE)
//                .paymentMode(NexusRequest.PaymentMode.YEARLY)
//                .commencementDate(date("02/05/2024"))
//                .firstPremiumDate(date("02/11/2026"))
//                .calculationDate(date("01/07/2026"))
//                .build());

        return builder.toString();
    }

    private void runCase(StringBuilder builder, Nexus nexus, String title, NexusRequest request) {
        NexusResult result = nexus.calculate(request);
        builder.append(title).append("\n");
        builder.append("Success: ").append(result.isSuccess()).append("\n");
        builder.append("Message: ").append(result.getMessage()).append("\n");
        builder.append("Plan: ").append(result.getPlanNo()).append(" ").append(result.getPlanName()).append("\n");
        builder.append("Premium: ").append(round(result.getPremium())).append("\n");
        builder.append("Modal Premium: ").append(round(result.getModalPremium())).append("\n");
        builder.append("Premium With Tax: ").append(round(result.getPremiumWithTax())).append("\n");
        builder.append("Maturity: ").append(round(result.getMaturityValue())).append("\n");
        builder.append("GSV: ").append(round(result.getGuaranteedSurrenderValue())).append("\n");
        builder.append("SSV: ").append(round(result.getSpecialSurrenderValue())).append("\n");
        builder.append("Surrender: ").append(round(result.getSurrenderValue())).append("\n");
        builder.append("Loan: ").append(round(result.getLoanValue())).append("\n");
        builder.append("Completed Years: ").append(result.getCompletedYears()).append("\n\n");

        Log.d(TAG, title + " success=" + result.isSuccess()
                + " premium=" + result.getPremium()
                + " surrender=" + result.getSurrenderValue()
                + " loan=" + result.getLoanValue()
                + " message=" + result.getMessage());
    }

    private Date date(String value) {
        try {
            return DATE_FORMAT.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value, e);
        }
    }

    private long round(double value) {
        return Math.round(value);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
