package com.primax.job;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WorkerEstadoCheck extends BaseJobs implements Job {

	private static final Logger log = Logger.getLogger(WorkerEstadoCheck.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Inicio JOB Actualización estado CheckList");
		synchronized (lockAuth) {
			try {

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage() + " ERROR EN AUTORIZACION  :WorkerEstadoCheck ");
			} finally {

			}

		}
		System.out.println("Fin JOB Actualización estado CheckList");
	}
}
