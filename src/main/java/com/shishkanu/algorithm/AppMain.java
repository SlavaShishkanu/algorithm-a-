package com.shishkanu.algorithm;

import com.shishkanu.algorithm.config.Context;
import com.shishkanu.algorithm.ui.AlgorithmApplicatonUI;

public class AppMain {

    public static void main(String[] args) {
        Context context = Context.getInstance();
        context.init();
        AlgorithmApplicatonUI.main(args);
    }

}
