Vue.component("ProfileFishingInstructor", {
    data: function() {
        return {
            activeUser:"",
            dateFrom:"",
            dateUntil: "",
            timeFrom: "",
            timeUntil: "",
            instructorUser: "",
            availabilityEdit: { dateFrom: "", dateUntil: "", timeFrom: "", timeUntil: "", instructorsId: ""},
            sendCheck:false,
            textAreaDelete: "",
        }
    },
    template :`
    <div>
    <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6 my-col">
            <span class="input-group-text" style="margin-top: 2%;">Ime:</span>
            <input type="text" id="name" disabled class="form-control" v-model="activeUser.name" placeholder="Unesite ime...">
            <span class="input-group-text" style="margin-top: 2%;">Prezime:</span>
            <input type="text" id="surname" disabled class="form-control" v-model="activeUser.surname" placeholder="Unesite prezime...">
            <span class="input-group-text" style="margin-top: 2%;">E-mail adresa:</span>
            <input type="text" id="email" disabled class="form-control" v-model="activeUser.email" placeholder="Unesite korisničko ime...">
            <span class="input-group-text" style="margin-top: 2%;">Lozinka:</span>
            <input type="password" id="password" disabled class="form-control" v-model="activeUser.password" placeholder="Unesite lozinku...">
            <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
            <input type="text" id="address" disabled  class="form-control" v-model="activeUser.address" placeholder="Unesite adresu (ulica, broj)...">
            <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
            <input type="text" id="city" disabled class="form-control" v-model="activeUser.city" placeholder="Unesite grad...">
            <span class="input-group-text" style="margin-top: 2%;">Država:</span>
            <input type="text" id="country" disabled class="form-control" v-model="activeUser.country" placeholder="Unesite državu...">
            <span class="input-group-text" style="margin-top: 2%;">Broj telefona:</span>
            <input type="text" id="phone" disabled class="form-control" v-model="activeUser.phoneNumber" placeholder="Unesite broj telefona...">
        </div>
        <div class="col-sm-6 my-col">
        	<div class="row my-row">
                <span class="input-group-text" style="margin-top: 1%;">Biografija:</span>
                <textarea class="form-control" id="biography" disabled v-model="activeUser.biography" rows="5" placeholder="Unesite biografiju..."></textarea>
            </div>
            <div class="row my-row" style="margin-top: 2%;">
                <div class="col col-sm-3">
                    <input type="date" class="form-control" id="dateFrom" v-model="dateFrom" disabled placeholder="Datum-od">
                      <span class="input-group-text">Datum-od</span>
                  </div>
                  <div class="col col-sm-3">
                    <input type="time" class="form-control" id="timeFrom" v-model="timeFrom" disabled placeholder="Vreme-od">
                      <span class="input-group-text">Vreme-od</span> 
                  </div>
                  <div class="col col-sm-3">
                    <input type="date"class="form-control" id="dateUntil" v-model="dateUntil" disabled placeholder="Datum-do">
                      <span class="input-group-text">Datum-do</span>
                  </div>
                  <div class="col col-sm-3">
                    <input type="time" class="form-control" id="timeUntil" v-model="timeUntil" disabled placeholder="Vreme-do">
                      <span class="input-group-text">Vreme-do</span> 
                  </div>
            </div>
            <div class="row my-row" style="margin-top: 2%;">
                <div class="col col-sm-4">
                  </div>
                <div class="col col-sm-4">
                    <button type="button" style="margin-top: 2%; margin-left: 7%;" v-on:click="enableAvailability()" class="btn btn-secondary btn-lg">Promeni dostupnost</button>
                  </div>
                  <div class="col col-sm-4">
                    <button type="button" style="margin-top: 2%; margin-left: 7%;" v-on:click="saveAvailability()" class="btn btn-primary btn-lg">Sačuvaj dostupnost</button>
                  </div>
            </div>
            <div style="margin-top: 5%; margin-left: 15%;">
                <img src="../images/myinfo.png" height="250" width="400">
            </div>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" v-on:click="enableInfoEdit()" class="btn btn-secondary btn-lg">Promeni podatke</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" v-on:click="saveInfoEdit()" class="btn btn-primary btn-lg">Sačuvaj izmene</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" data-bs-toggle="modal" data-bs-target="#deleteAccountReq" class="btn btn-danger btn-lg">Zahtev za brisanje naloga</button>
        </div>
    </div>
</div>

<!-- Modal za slanje zahteva za brisanje naloga -->
	<div class="modal fade" id="deleteAccountReq" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Zahtev za brisanje naloga</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
	        </button>
	      </div>
	      <div class="modal-body">
	        <span class="input-group-text" style="margin-top: 1%;">Razlozi brisanja naloga:</span>
            <textarea class="form-control" v-model="textAreaDelete" rows="8" placeholder="Unesite razloge zbog kojih brišete nalog..."></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" v-on:click="sendRequest()">Pošalji zahtev</button>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Otkaži</button>
	      </div>
	    </div>
	  </div>
	</div>

</div>
</div>	  
    	`
    	,
    methods: {
    	saveInfoEdit() {
    		if (this.checkInfoEdit()) {
        	axios
				.post('/fishingInstructor/editInstructor', this.activeUser)	
				.then(response => {
	                	this.activeUser = response.data
			 			localStorage.setItem("activeUser", JSON.stringify(this.activeUser));
		        });	
		        this.disableInfoEdit()
		        Swal.fire({ icon: 'success', title: 'Uspešno ste sačuvali izmene !', showConfirmButton: false, timer: 1500 })
	        }
	        else {
	            Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke !'})
	        }
        },
        
        saveAvailability() {
        	if (this.checkDateTimeValidity()) {
        		this.fillAvailabilityEdit()
    			this.availabilityEdit.instructorsId = this.activeUser.id
    			axios
				.post('/fishingInstructor/editInstructorsAvailability', this.availabilityEdit)	
				.then(response => {
	                	this.activeUser = response.data
			 			localStorage.setItem("activeUser", JSON.stringify(this.activeUser));
			 			this.updateUserInfoOnFront()
			 			Swal.fire({ icon: 'success', title: 'Uspešno ste ažurirali vreme dostupnosti !', showConfirmButton: false, timer: 1500 })
		        });	
        	this.disableAvailability()
        	}
        	else {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Uneti datumi nisu validni. Datum i vreme od kog ste dostupni mora biti pre datuma i vremena do kog ste dostupni !'})
        	}
        },
        
        fillAvailabilityEdit() {
        	this.availabilityEdit.dateFrom = document.getElementById("dateFrom").value
			this.availabilityEdit.dateUntil = document.getElementById("dateUntil").value
			this.availabilityEdit.timeUntil = document.getElementById("timeUntil").value
			this.availabilityEdit.timeFrom = document.getElementById("timeFrom").value
        },
        
        checkDateTimeValidity() {
        	var date1 = new Date(this.dateFrom + ' ' + this.timeFrom);
        	var date2 = new Date(this.dateUntil + ' ' + this.timeUntil);
        	console.log(this.dateFrom)
        	console.log(this.timeFrom)
			if(date1.getTime() > date2.getTime() || date1.getTime() === date2.getTime() || this.dateFrom == "" || this.dateUntil == "" || this.timeFrom == "" || this.timeUntil == ""){
			    return false
			}
			else {
				return true
			}
        },
        
        sendRequest(){
        	if (this.textAreaDelete == "") {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Popunite polje za razlog brisanja naloga !'})
        	} else {
        	if (!this.sendCheck) {
	            axios
	            .post('/appUser/sendRequest/' + this.textAreaDelete + "/" + this.activeUser.id)
	            .then(response=>{
	                //window.location.reload()
	                this.editClick = false
	                this.sendCheck = response.data
	                $('#deleteAccountReq').modal('hide');
	                this.textAreaDelete = ""
	            })
	            .catch(error=>{
	                console.log("Greska.")	
	                alert("Podaci su lose uneti.")
	                window.location.reload()
	            })
	        }
	        else {
	        	Swal.fire({icon: 'error', title: 'Greška', text: 'Već ste poslali zahtev za brisanje naloga !'})
	        }
	        }

        },
        
        updateUserInfoOnFront() {
        	let from = this.activeUser.availableFrom.split("T")
	    	let until = this.activeUser.availableUntil.split("T")
	    	this.dateFrom = from[0]
	    	console.log(this.dateFrom)
	    	this.timeFrom = from[1]
	    	console.log(this.timeFrom)
	    	this.dateUntil = until[0]
	    	console.log(this.dateUntil)
	    	this.timeUntil = until[1]
	    	console.log(this.timeUntil)
        },
       
        checkInfoEdit(){
        	if (this.activeUser.name !== "" && this.activeUser.surname !== "" &&  this.activeUser.email !== "" && 
        	this.activeUser.password !== "" && this.activeUser.address !== "" && this.activeUser.city !== "" && 
        	this.activeUser.country !== "" && this.activeUser.phoneNumber !== "" && this.activeUser.biography !== "")
        		return true;
        	else
        		return false;
        },
        
    	disableAvailability() {
    			document.getElementById("dateFrom").disabled = true
    			document.getElementById("dateUntil").disabled = true
    			document.getElementById("timeUntil").disabled = true
    			document.getElementById("timeFrom").disabled = true
    	},
    	
    	enableAvailability() {
    			document.getElementById("dateFrom").disabled = false
    			document.getElementById("dateUntil").disabled = false
    			document.getElementById("timeUntil").disabled = false
    			document.getElementById("timeFrom").disabled = false
    	},
    	enableInfoEdit() {
    			document.getElementById("name").disabled = false 
    			document.getElementById("surname").disabled = false 
    			document.getElementById("email").disabled = false 
    			document.getElementById("password").disabled = false 
    			document.getElementById("address").disabled = false 
    			document.getElementById("country").disabled = false 
    			document.getElementById("city").disabled = false 
    			document.getElementById("phone").disabled = false 
    			document.getElementById("biography").disabled = false
    	},
    	disableInfoEdit() {
    			document.getElementById("name").disabled = true 
    			document.getElementById("surname").disabled = true 
    			document.getElementById("email").disabled = true 
    			document.getElementById("password").disabled = true 
    			document.getElementById("address").disabled = true 
    			document.getElementById("country").disabled = true 
    			document.getElementById("city").disabled = true 
    			document.getElementById("phone").disabled = true 
    			document.getElementById("biography").disabled = true
    	},
    	
    },
    mounted(){
    	 this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')

        userId = this.activeUser.id 
        axios
        .get('/appUser/requestExists/' + userId)
        .then(response=>{
            //window.location.reload()
            this.sendCheck = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
    	console.log(this.activeUser)
    	let from = this.activeUser.availableFrom.split("T")
    	let until = this.activeUser.availableUntil.split("T")
    	this.dateFrom = from[0]
    	console.log(this.dateFrom)
    	this.timeFrom = from[1].substring(0,5)
    	console.log(this.timeFrom)
    	this.dateUntil = until[0]
    	console.log(this.dateUntil)
    	this.timeUntil = until[1].substring(0,5)
    	console.log(this.timeUntil)
    },

});