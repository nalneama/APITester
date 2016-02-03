package com.nasserapps.apitester.AI.CustomNotificationStatement;

import com.nasserapps.apitester.Model.Ticker;


public abstract class Rule {
        public boolean evaluate(Ticker stock){
            return false;
        }
}
