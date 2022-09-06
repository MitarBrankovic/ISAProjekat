Vue.component("CurrentBoatReservations", {
    data: function() {
        return {
            dateTimeNow: new Date(),
            appointments: "",
            newAppointment: {dateFrom: "", timeFrom: "8:00", dateUntil: "", timeUntil: "12:00", extraNotes: "", price: 100, cottageId: 0, clientId: ""},
            boats: "",
        }
    },
    template :`
    <div>
    <div class="container-fluid">
    <h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c">Rezervacije u toku</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Avantura</td>
            <td>Adresa</td>
            <td>Datum i vreme početka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Ocena</td>
            <td>Klijent</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="appointment in appointments">
            <td>{{appointment.boat.name}}</td>
            <td>{{appointment.boat.address}}</td>
            <td>{{appointment.appointmentStart.substring(8,10)}}.{{appointment.appointmentStart.substring(5,7)}}.{{appointment.appointmentStart.substring(0,4)}}. {{appointment.appointmentStart.substring(11,13)}}:{{appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{appointment.duration}} h</td>
            <td>{{appointment.maxAmountOfPeople}}</td>
            <td>{{appointment.extraNotes}}</td>
            <td>{{appointment.price}} din.</td>
            <td v-if="appointment.rating == 0">-/5</td>
            <td v-else>{{appointment.rating}}/5</td>
            <td>{{appointment.client.name}} {{appointment.client.surname}}</td>
            <td><button type="button" data-bs-toggle="modal" data-bs-target="#newAppointment" v-on:click="getClientId(appointment.client.id)" class="btn btn-success">Zakazi termin klijentu</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    <div class="modal fade" id="newAppointment" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLongTitle">Akcija</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<div class="row my-row" style="margin-top: 2%;">
            		<span class="input-group-text" id="basic-addon1">Brod</span>
            		<select class="form-select" v-model="newAppointment.boatId" aria-label="Example select with button addon">
			                  <option v-for="boat in boats" :value="boat.id">{{boat.name}} - {{boat.address}}</option>
			                </select>
            	</div>
                <div class="row my-row" style="margin-top: 2%;">
                    <div class="col col-sm-6">
                        <input type="date" v-model="newAppointment.dateFrom" class="form-control" placeholder="Datum-od">
                          <span class="input-group-text" id="basic-addon1">Datum-od</span>
                      </div>
                      <div class="col col-sm-6">
			                <select class="form-select" v-model="newAppointment.timeFrom" aria-label="Example select with button addon">
			                  <option value="8:00">8:00</option>
			                  <option value="9:00">9:00</option>
			                  <option value="10:00">10:00</option>
			                  <option value="11:00">11:00</option>
			                  <option value="12:00">12:00</option>
			                  <option value="13:00">13:00</option>
			                  <option value="14:00">14:00</option>
			                  <option value="15:00">15:00</option>
			                  <option value="16:00">16:00</option>
			                  <option value="17:00">17:00</option>
			                  <option value="18:00">18:00</option>
			                  <option value="19:00">19:00</option>
			                  <option value="20:00">20:00</option>
			                </select>
                          <span class="input-group-text" id="basic-addon1">Vreme-od</span> 
                          
                      </div>
                </div>
                <div class="row my-row" style="margin-top: 2%;">
                      <div class="col col-sm-6">
                        <input type="date" class="form-control" v-model="newAppointment.dateUntil" placeholder="Datum-do">
                          <span class="input-group-text" id="basic-addon1">Datum-do</span>
                      </div>
                      <div class="col col-sm-6">
                        <select class="form-select" v-model="newAppointment.timeUntil" aria-label="Example select with button addon">
			                  <option value="8:00">8:00</option>
			                  <option value="9:00">9:00</option>
			                  <option value="10:00">10:00</option>
			                  <option value="11:00">11:00</option>
			                  <option value="12:00">12:00</option>
			                  <option value="13:00">13:00</option>
			                  <option value="14:00">14:00</option>
			                  <option value="15:00">15:00</option>
			                  <option value="16:00">16:00</option>
			                  <option value="17:00">17:00</option>
			                  <option value="18:00">18:00</option>
			                  <option value="19:00">19:00</option>
			                  <option value="20:00">20:00</option>
			                </select>
                          <span class="input-group-text" id="basic-addon1">Vreme-do</span> 
                      </div>
                </div>
              <span class="input-group-text" style="margin-top: 2%;">Dodatne usluge:</span>
              <textarea class="form-control" id="usernameInput" v-model="newAppointment.extraNotes" rows="3" placeholder="Unesite dodatne usluge..."></textarea>
              <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Cena:</span>
              <input type="number" min="100" max="100000" step="100" v-model="newAppointment.price" class="form-control" placeholder="Unesite cenu...">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Otkaži</button>
              <button type="button" v-on:click="addNewAppointment()" class="btn btn-danger">Zakaži termin</button>
            </div>
          </div>
        </div>
      </div>
      
    </div>	  
    	`
    	,
    methods: {
    	
    	getClientId(id) {
    		this.newAppointment.clientId = id;
    	},
    	
    	async checkOverlap() {
        	var bool = false;
        	await axios
               .post('/boatAppointments/checkOverlap', this.newAppointment)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },
    	
        
        checkDate() {
        	var timeFrom = this.newAppointment.timeFrom.split(":")[0]
        	var timeUntil = this.newAppointment.timeUntil.split(":")[0]
        	var DateFrom = new Date(this.newAppointment.dateFrom.substring(0,4), this.newAppointment.dateFrom.substring(5,7), this.newAppointment.dateFrom.substring(8,10), timeFrom, 0)
			var DateUntil = new Date(this.newAppointment.dateUntil.substring(0,4), this.newAppointment.dateUntil.substring(5,7), this.newAppointment.dateUntil.substring(8,10), timeUntil, 0)
        	console.log(DateFrom)
        	console.log(DateUntil)
        	return DateUntil > DateFrom       	
        },
    	
    	async addNewAppointment(){
    	console.log(this.checkNewAppointment())
    	var overlap = await this.checkOverlap()
            if (this.checkNewAppointment()) {
            	if (this.checkDate()){
	            	if (overlap) {
	            		if (1==1) {
	            			this.addAppointment();
	           			}
	           			else{
	         	  			Swal.fire({icon: 'error', title: 'Greška', text: 'Niste dostupni u željeno vreme !'})
	             		}
	         		}
	       			else{
	     	  			Swal.fire({icon: 'error', title: 'Greška', text: 'Termin se preklapa sa nekim drugim vašim terminom !'})
	         	    }
	         	}
	         	else {
	         		Swal.fire({icon: 'error', title: 'Greška', text: 'Datum i vreme nisu validni !'})
	         	}
           }
           else {
         	  Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li je validna cena (> 99)!'})
           }
        },
        
        addAppointment() {
        	axios
               .post('/boatAppointments/addOwnersAppointmentForClient', this.newAppointment,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
               .then(response=>{
                  this.resetData();
                  Swal.fire({ icon: 'success', title: 'Uspešno ste rezervisali termin za klijenta !', showConfirmButton: false, timer: 1500 })
                  })
               .catch(error=>{
                   console.log("Greska.")	
                   alert("Podaci su lose uneti.")
                   window.location.reload()
               })
        } ,
        
        async checkOverlap() {
        	var bool = false;
        	await axios
               .post('/boatAppointments/checkOverlap', this.newAppointment)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },
        
        resetData() {
        	  this.newAppointment.dateFrom = ""
              this.newAppointment.timeFrom = "8:00"
              this.newAppointment.dateUntil = ""
              this.newAppointment.timeUntil = "12:00"
              this.newAppointment.extraNotes = ""
        },
        
        checkNewAppointment(){
        	if (this.newAppointment.boatId != 0  && this.newAppointment.dateFrom !== "" && this.newAppointment.timeFrom !== "" &&  this.newAppointment.dateUntil !== "" && 
        	this.newAppointment.timeUntil !== "" && this.newAppointment.extraNotes !== "" && this.newAppointment.price !== "" &&
        	this.newAppointment.price > 99)
        		return true;
        	else
        		return false;
        },
        
    },
    mounted() {
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'ship_owner')
            this.$router.push('/')
           console.log(this.dateTimeNow)
         axios
            .get("boatAppointments/getCurrentAppointments/" + this.activeUser.id)
	        .then(response => (this.appointments = response.data, console.log("neam")))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        axios
        	.get('/boats/getOwnersBoats/' + this.activeUser.id)
	        .then(response => (
	        this.boats = response.data
	        ))
	        .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
    },

});