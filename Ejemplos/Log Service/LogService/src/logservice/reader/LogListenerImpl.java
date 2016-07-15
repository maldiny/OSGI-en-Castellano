package logservice.reader;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

import logservice.writer.LogServiceWritter;

public class LogListenerImpl implements LogListener {

	private final LogService logService;

	public LogListenerImpl(LogService logService) {
		this.logService = logService;
	}

	public void logged(LogEntry log){
	  if(log != null && log.getMessage() != null && log.getMessage().contains("LogServiceWritter")){
		  logService.log(LogService.LOG_INFO, "[" + this.getClass().getName() + "] Message: " + log.getMessage().split("]")[1]);
	  }
	}

}