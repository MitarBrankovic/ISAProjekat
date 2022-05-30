Vue.component("InstructorsCalendar", {
    data: function() {
        return {
            activeUser:"",
  			selectedDate: new Date(),
  			appointments: [],
  			calendar: "",
			data: {
				events: [],
			},
			availabilityData: {
				 events: [],
				 id: 1,
			},
			availabilityEdit: { dateFrom: "", dateUntil: "", timeFrom: "08:00", timeUntil: "00:00", instructorsId: ""},
		   }
    },
    template :`
    <div>


<br>
<div class="row my-row  justify-content-around">
            <div class="col-sm-10 my-col">
                <div id='calendar'></div>
              </div>
			<div class="col-sm-2">
                <button style="margin-top: 2%" type="button" v-on:click="saveAvailability()" class="btn btn-success btn-md">Ažuriraj dostupnost</button>
              </div>
</div>
		
	</div>	  
    	`
    	,
    methods: {
    saveAvailability(start, end) {
    			if (localStorage.getItem('start') == null || localStorage.getItem('end') == null) {
						Swal.fire({ icon: 'error', title: 'Morate selektovati period vaše dostupnosti !', showConfirmButton: true})
    			}
    			else {
    				this.availabilityEdit.instructorsId = this.activeUser.id
    				this.availabilityEdit.dateFrom = localStorage.getItem('start')
    				this.availabilityEdit.dateUntil = localStorage.getItem('end')
	    			axios
					.post('/fishingInstructor/editInstructorsAvailability', this.availabilityEdit)	
					.then(response => {
		                	this.activeUser = response.data
				 			localStorage.setItem("activeUser", JSON.stringify(this.activeUser));
				 			localStorage.removeItem('start');
				 			localStorage.removeItem('end');
				 			this.refreshAvailabilityCalendar()
				 			Swal.fire({ icon: 'success', title: 'Uspešno ste ažurirali vreme dostupnosti !', showConfirmButton: false, timer: 1500 })
		        });	
        	}
        },
        
        loadAvailabilityCalendar() {
        		var obj = 
        		{
				  title: "Dostupan",
   				  start: this.activeUser.availableFrom,
   				  end: this.activeUser.availableUntil,
   				  backgroundColor: "blue"
				}
				this.availabilityData.events.push(obj)
        },
        
        refreshAvailabilityCalendar() {
        	this.availabilityData.events = []
        		var obj = 
        		{
				  title: "Dostupan",
   				  start: this.activeUser.availableFrom,
   				  end: this.activeUser.availableUntil,
   				  backgroundColor: "blue"
				}
				this.availabilityData.events.push(obj)
				calendar.getEventSourceById(1).remove()
				calendar.addEventSource(this.availabilityData)
				
        },

    		loadCalendar() {
			document.addEventListener('DOMContentLoaded', function() {
		  var calendarEl = document.getElementById('calendar');
		
		   calendar = new FullCalendar.Calendar(calendarEl, {
		    initialView: 'dayGridMonth',
		    initialDate: '2022-05-29',
		    dayMaxEvents: true,
		    contentHeight: 600,
		    height: 800,
		    selectable: true,
		    buttonText: {
			  today:    'Danas',
			  month:    'Mesec',
			  week:     'Nedelja',
			  day:      'Dan',
			  list:     'Lista'
			},
		    headerToolbar: {
				left: 'prevYear,prev,next,nextYear today',
		      center: 'title',
		      right: 'dayGridMonth,timeGridWeek,timeGridDay,list'
		    },
		    dateClick: function(info) {
		    	calendar.changeView('list', info.dateStr);
			  },
			select: function(info) {
		        localStorage.setItem('start', info.startStr);
		        localStorage.setItem('end', info.endStr);
		    },
		    eventClick: function(info) {
		    	Swal.fire({ icon: 'info', title: info.event.title, showConfirmButton: true, text: moment(info.event.start).format("MMMM Do YYYY, h:mm a") + " - " + moment(info.event.end).format("MMMM Do YYYY, h:mm a")})
		      }
			  		  });
		  calendar.render();
		});
		},
		
		formatCalendarData() {
			this.data.events = []
			for (var i = 0; i < this.appointments.length; i ++ ){
				
				const endDate = new Date(this.appointments[i].appointmentStart)
				endDate.setTime(endDate.getTime() + this.appointments[i].duration * 60 * 60 * 1000);
				let monthNum = endDate.getMonth() + 1
				let monthString = monthNum.toString()
				if (monthNum < 10)
					monthString = "0" + monthNum.toString()
				var pieces = endDate.toString().split(" ") 
				var obj = {
				  title: this.appointments[i].client.name + " " + this.appointments[i].client.surname + "\n" +
				  this.appointments[i].fishingAdventure.name + "\n" + this.appointments[i].price + " RSD",
   				  start: this.appointments[i].appointmentStart,
   				  end: pieces[3] + "-" + monthString + "-" + pieces[2] + "T" + pieces[4],
   				  backgroundColor: "green"
				}
   				this.data.events.push(obj)
			}
		},
    }, 
    
    mounted () {
       this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')
         axios
            .get("fishingAppointments/getReservationsHistory/" + this.activeUser.id)
	        .then(response => {
	            this.appointments = response.data,
	            this.formatCalendarData()
	            this.loadAvailabilityCalendar()
	            calendar.addEventSource(this.data)
	            calendar.addEventSource(this.availabilityData)
	            })
            .catch(error=>{
                window.location.reload()
            })
            this.loadCalendar()            
  },
  
  

}


    
    
    
    
);



