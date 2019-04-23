import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Message } from 'framework-lib';

@Injectable()
export class ToastMessageService {

    constructor(private toastr: ToastrService) {
    }

    
    show(message: Message) {
    	if (message != null) { 
	    	switch (message.severity) {
	    		case 'info':
	            	this.toastr.success(message.message, message.title);
	            	break;
	    		case 'warning':
	            	this.toastr.warning(message.message, message.title);
	            	break;
	            case 'error':
	            	this.toastr.error(message.message, message.title);
	            	break;
	        }
        }
    }
   
}
