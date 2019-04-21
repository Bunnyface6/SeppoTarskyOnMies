/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.WorkPerformance;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class WorkPerformanceCont {
    
    private ArrayList<WorkPerformance> recentWorkPerformances = new ArrayList<WorkPerformance>();
    
    private WorkPerformance lastUsed;
    
    public WorkPerformance createWorkPerformance(int nmbr, int assistanceWork, int desingWork, int work, int discountPer, int workSiteNmbr) {
        WorkPerformance x = new WorkPerformance(nmbr, assistanceWork, desingWork, work, discountPer, workSiteNmbr);
        recentWorkPerformances.add(x);
        lastUsed = x;
        return x;
    }
    
    
    
}
