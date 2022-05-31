Vue.component("BoatOwnersCalendar", {
    data: function() {
        return {
            activeUser:"",
  			selectedDate: new Date(),
  			appointments: [],
			boats: [],
  			calendar: "",
			data: {
				events: [],
			},
			availabilityData: {
				 events: [],
				 id: 1,
			},
			availabilityEdit: { dateFrom: "", dateUntil: "", timeFrom: "08:00", timeUntil: "00:00", entityId: "", ownerId: ""},
			selectedBoatId: "",
		   }
    },
    template :`
    <div>


<br>
<div class="row my-row  justify-content-around">
            <div class="col-sm-9 my-col">
                <div id='calendar'></div>
              </div>
			<div class="col-sm-1">
                <button style="margin-top: 2%" type="button" v-on:click="saveAvailability()" class="btn btn-success btn-md">Ažuriraj dostupnost</button>
              </div>
			<div class="col-sm-2">
                <span class="input-group-text" id="basic-addon1">Dostupnost - Brod</span>
            		<select class="form-select" v-model="selectedBoatId" @change="refreshAvailabilityCalendar()" aria-label="Example select with button addon">
			                  <option v-for="boat in boats" :value="boat.id">{{boat.name}} - {{boat.address}}</option>
			                </select>
              </div>
</div>
		
	</div>	  
    	`
    	,
    methods: {
    saveAvailability() {
    			if (localStorage.getItem('start') == null || localStorage.getItem('end') == null) {
						Swal.fire({ icon: 'error', title: 'Morate selektovati period dostupnosti broda!', showConfirmButton: true})
    			}
    			else {
    				this.availabilityEdit.entityId = this.selectedBoatId
    				this.availabilityEdit.dateFrom = localStorage.getItem('start')
    				this.availabilityEdit.dateUntil = localStorage.getItem('end')
	    			axios
					.post('/boats/editBoatsAvailability', this.availabilityEdit)	
					.then(response => {
		                	this.boats = response.data
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
   				  start: this.boats[0].availableFrom,
   				  end: this.boats[0].availableUntil,
   				  backgroundColor: "blue"
				}
				this.selectedBoatId = this.boats[0].id
				this.availabilityData.events.push(obj)
        },
        
        refreshAvailabilityCalendar() {
		    this.availabilityData.events = []
			for (var i = 0; i < this.boats.length; i ++ ){
				if (this.boats[i].id == this.selectedBoatId) {	
	        	this.availabilityData.events = []
	        		var obj = 
	        		{
					  title: "Dostupan",
	   				  start: this.boats[i].availableFrom,
	   				  end: this.boats[i].availableUntil,
	   				  backgroundColor: "blue"
					}
					this.availabilityData.events.push(obj)
					calendar.getEventSourceById(1).remove()
					calendar.addEventSource(this.availabilityData)
				}
			}
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
				  this.appointments[i].boat.name + "\n" + this.appointments[i].price + " RSD",
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
		this.availabilityEdit.ownerId = this.activeUser.id
        if(this.activeUser.role != 'ship_owner')
            this.$router.push('/')
         axios
            .get("boatAppointments/getReservationsHistory/" + this.activeUser.id)
	        .then(response => {
	            this.appointments = response.data,
	            this.formatCalendarData(),
	            calendar.addEventSource(this.data),
				console.log(this.appointments)
	            })
            .catch(error=>{
                window.location.reload()
            })
		axios
            .get("boats/getOwnersBoats/" + this.activeUser.id)
	        .then(response => {
	            this.boats = response.data,
	            this.loadAvailabilityCalendar(),
	            calendar.addEventSource(this.availabilityData),
				console.log(this.availabilityData)
	            })
            .catch(error=>{
                window.location.reload()
            })
            this.loadCalendar()            
  },
}


    
    
    
    
);



