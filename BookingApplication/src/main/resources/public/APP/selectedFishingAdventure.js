Vue.component("SelectedFishingAdventure", {
    data: function() {
        return {
            adventure: "",
            pricelistIdRemove: "",
            adventureIdRemove: "",
            userId:"",
            quickAppointment: {dateFrom: "", timeFrom: "", dateUntil: "", timeUntil: "", address: "", 
            city: "", maxAmountOfPeople: 1, extraNotes: "", price: 100, adventureId: 0 },
            appointments: "",
            pricelist: "",
            newPricelistItem: {instructorsId: "", description: "", price: 50},
            removeItemObject: {itemId: "", instructorId: ""},
        }
    },
    template :`
    <div>
    
    <h1>{{adventure.name}}</h1>
    <p>Adresa: {{adventure.address}}, {{ adventure.city}}
    <br> Opis : {{adventure.description}}
    <br> Kapacitet :{{adventure.maxAmountOfPeople}} osoba
    <br> Pravila : {{adventure.behaviourRules}}
    <br> Oprema : {{adventure.equipment}}
    <br> Cena po satu : {{adventure.pricePerHour}} din/h
    <br> Procenat za otkazivanje : {{adventure.cancellingPrecentage}} %
    <br> Ocena : {{adventure.rating}} /5
    </p>
    <div class="container-fluid">
    <table class="table">
        <thead>
        <tr>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Stanje</td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="appointment in appointments" v-if="appointment.appointmentType == 'quick'">
            <td>{{appointment.appointmentStart}}</td>
            <td>{{appointment.duration}} h</td>
            <td>{{appointment.maxAmountOfPeople}}</td>
            <td>{{appointment.extraNotes}}</td>
            <td>{{appointment.price}} din.</td>
            <td v-if="appointment.client==null && activeUser.role == 'client'"><button type="button" class="btn btn-success" v-on:click="scheduleAppointment(appointment)">Zakazi</button> </td>
            <td v-else-if="appointment.client == null && activeUser.role == 'fishing_instructor'"><p style="color:green;">Slobodno</p></td>
            <td v-else><p style="color:red;">Zakazano</p> </td>
            </tr>
        </tbody>
    </table>
    </div>
    <button type="button" style="margin-top: 3%; margin-bottom: 3%;"   data-bs-toggle="modal" data-bs-target="#newAppointment" class="btn btn-danger btn-lg">Dodaj brzu rezervaciju</button>
	<h2>Cenovnik dodatnih usluga</h2>
	
	<div class="container-fluid" style="margin-top: 3%">
		<table class="table">
	        <thead>
	        	<tr>
	            	<td scope="col">Opis</td>
	            	<td scope="col">Cena</td>
	            	<td scope="col"></td>
	        	</tr>
	        </thead>
	        <tbody>
	            <tr v-for="p in pricelist">
		            <td>{{p.description}}</td>
		            <td>{{p.price}} din.</td>
		            <td><button type="button" data-bs-toggle="modal" data-bs-target="#areYouSure" v-on:click="prepareItemToRemove(p.id)" class="btn btn-danger">Obriši</button> </td>
	            </tr>
	            <tr>
		            <td><input type="text" class="form-control" v-model="newPricelistItem.description" placeholder="Unesite opis..."></td>
		            <td><input type="number" min = "50" step="50" class="form-control" v-model="newPricelistItem.price" placeholder="Unesite cenu..."></td>
		            <td><button type="button" v-on:click="addPricelistItem()" class="btn btn-success">Dodaj novu uslugu</button> </td>
	            </tr>
	        </tbody>
	    </table>
	</div>
	
		<!-- Modal za potvrdu brisanja stavke cenovnika -->
	<div class="modal fade" id="areYouSure" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Potvrda</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
	        </button>
	      </div>
	      <div class="modal-body">
	        <h2>Da li ste sigurni da želite da obrišete ovu stavku ?</h2>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Ne</button>
	        <button type="button" class="btn btn-danger" v-on:click="removeItem()" data-bs-dismiss="modal" >Da</button>
	      </div>
	    </div>
	  </div>
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
                    <div class="col col-sm-6">
                        <input type="date" v-model="quickAppointment.dateFrom" class="form-control" placeholder="Datum-od">
                          <span class="input-group-text" id="basic-addon1">Datum-od</span>
                      </div>
                      <div class="col col-sm-6">
                        <input type="time" class="form-control" v-model="quickAppointment.timeFrom"  placeholder="Datum-od">
                          <span class="input-group-text" id="basic-addon1">Vreme-od</span> 
                      </div>
                </div>
                <div class="row my-row" style="margin-top: 2%;">
                      <div class="col col-sm-6">
                        <input type="date" class="form-control" v-model="quickAppointment.dateUntil" placeholder="Datum-do">
                          <span class="input-group-text" id="basic-addon1">Datum-do</span>
                      </div>
                      <div class="col col-sm-6">
                        <input type="time" class="form-control" v-model="quickAppointment.timeUntil" placeholder="Datum-od">
                          <span class="input-group-text" id="basic-addon1">Vreme-do</span> 
                      </div>
                </div>
              <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Adresa:</span>
              <input type="text" class="form-control" v-model="quickAppointment.address" placeholder="Unesite adresu...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Grad:</span>
              <input type="text" class="form-control" v-model="quickAppointment.city" placeholder="Unesite grad...">
              <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
              <input type="number" min="1" max="30" v-model="quickAppointment.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
              <span class="input-group-text" style="margin-top: 2%;">Dodatne usluge:</span>
              <textarea class="form-control" id="usernameInput" v-model="quickAppointment.extraNotes" rows="3" placeholder="Unesite dodatne usluge..."></textarea>
              <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Cena:</span>
              <input type="number" min="100" max="100000" step="100" v-model="quickAppointment.price" class="form-control" placeholder="Unesite cenu...">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Otkaži</button>
              <button type="button" v-on:click="addNewAppointment()" class="btn btn-danger">Dodaj akciju</button>
            </div>
          </div>
        </div>
      </div>
      
    </div>	  
    	`
    	,
    methods: {
        addNewAppointment(){
            this.quickAppointment.adventureId = this.adventure.id
            if (this.checkNewAppointment()){
	         	axios
	               .post('/fishingAppointments/addQuickAppointment', this.quickAppointment)
	               .then(response=>{
	                  this.appointments = response.data
	                  this.quickAppointment.dateFrom = ""
	                  this.quickAppointment.timeFrom = ""
	                  this.quickAppointment.dateUntil = ""
	                  this.quickAppointment.timeUntil = ""
	                  this.quickAppointment.address = ""
	                  this.quickAppointment.city = ""
	                  this.quickAppointment.extraNotes = ""
	               })
	               .catch(error=>{
	                   console.log("Greska.")	
	                   alert("Podaci su lose uneti.")
	                   window.location.reload()
	               })
           }
           else{
           	  Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li su validni maksimalni broj osoba (> 0) i cena (> 99)!'})
           }
        },
        
        scheduleAppointment:function(appointment){
          axios
          .put('/fishingAppointments/scheduleAdventureAppointment/' + appointment.id + "/" + this.activeUser.id)
          .then(response=>{
              window.location.reload()
          })
          .catch(error=>{
              console.log("Greska.")	
              alert("Podaci su lose uneti.")
              window.location.reload()

          })
        },
        
        addPricelistItem(){
        this.newPricelistItem.instructorsId = this.activeUser.id
        if (this.checkNewPricelistItem()){
         	axios
               .post('/pricelist/addPricelistItem', this.newPricelistItem)
               .then(response=>{
                  this.pricelist = response.data
               })
               .catch(error=>{
                   console.log("Greska.")	
                   alert("Podaci su lose uneti.")
                   window.location.reload()
               })
           }
           else {
           	   Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke ili je cena nevalidna !'})
           }
        },
        
        prepareItemToRemove(id){
        	this.pricelistIdRemove = id;		
        },
        
        removeItem(){
        this.removeItemObject.itemId = this.pricelistIdRemove
        this.removeItemObject.instructorId = this.activeUser.id
        console.log(this.removeItemObject)
         	axios
               .post('/pricelist/deletePricelistItem', this.removeItemObject)
               .then(response=>{
                  this.pricelist = response.data
               })
               
        },
        
        checkNewAppointment(){
        	if (this.quickAppointment.dateFrom !== "" && this.quickAppointment.timeFrom !== "" &&  this.quickAppointment.dateUntil !== "" && 
        	this.quickAppointment.timeUntil !== "" && this.quickAppointment.address !== "" && this.quickAppointment.city !== "" && 
        	this.quickAppointment.extraNotes !== "" && this.quickAppointment.maxAmountOfPeople !== "" && this.quickAppointment.price !== "" &&
        	this.quickAppointment.price > 99 && this.quickAppointment.maxAmountOfPeople > 0)
        		return true;
        	else
        		return false;
        },
        
        checkNewPricelistItem(){
        	if (this.newPricelistItem.description !== "" && this.newPricelistItem.price !== "" && this.newPricelistItem.price > 49) 
        		return true;
        	else
        		return false;
        },
        
        clearNewAppointment(){
            this.appointments = response.data
            this.quickAppointment.dateFrom = ""
            this.quickAppointment.timeFrom = ""
            this.quickAppointment.dateUntil = ""
            this.quickAppointment.timeUntil = ""
            this.quickAppointment.address = ""
            this.quickAppointment.city = ""
            this.quickAppointment.extraNotes = ""
    	},
    	clearNewPricelistItem(){
    		this.newPricelistItem.description = ""
    		this.newPricelistItem.price = 100
    	}
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')
        axios
            .get("fishingAdventures/getSelectedAdventure/" + this.$route.query.id)
	        .then(response => (this.adventure = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
         axios
            .get("fishingAppointments/getQuickFishingAppointments/" + this.$route.query.id)
	        .then(response => (this.appointments = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
         axios
            .get("pricelist/getInstructorsPricelist/" + this.activeUser.id)
	        .then(response => (this.pricelist = response.data))

    },

});