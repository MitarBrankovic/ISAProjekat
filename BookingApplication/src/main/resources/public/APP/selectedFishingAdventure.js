Vue.component("SelectedFishingAdventure", {
    data: function() {
        return {
            activeUser:"",
            adventure: "",
            pricelistIdRemove: "",
            adventureIdRemove: "",
            userId:"",
            quickAppointment: {dateFrom: "", timeFrom: "", dateUntil: "", timeUntil: "", extraNotes: "", price: 100, adventureId: 0 },
            appointments: "",
            pricelist: "",
            newPricelistItem: {instructorsId: "", description: "", price: 50},
            removeItemObject: {itemId: "", instructorId: ""},
            subscibedAdventures:[]
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
    <br> Ocena : {{adventure.rating}}/5
    </p><br>
    <button v-if="activeUser.role == 'client' && !exist()" type="submit" class="button" v-on:click="subscribe()">Pretplati se</button>
    <button v-if="activeUser.role == 'client' && exist()" type="submit" class="btn btn-danger" v-on:click="unsubscribe()">Odjavi se</button>

    <br><br><hr>
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
            <td>{{appointment.appointmentStart.substring(8,10)}}.{{appointment.appointmentStart.substring(5,7)}}.{{appointment.appointmentStart.substring(0,4)}}. {{appointment.appointmentStart.substring(11,13)}}:{{appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{appointment.duration}} h</td>
            <td>{{appointment.maxAmountOfPeople}}</td>
            <td>{{appointment.extraNotes}}</td>
            <td>{{appointment.price}} din.</td>
            <td v-if="appointment.client==null && activeUser.role == 'client'"><button id="scheduleButton" v-if="checkUserandPenalties()" type="button" class="btn btn-success" v-on:click="scheduleAppointment(appointment)">Zakazi</button> </td>
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
	                  this.quickAppointment.extraNotes = ""
	               })
	               .catch(error=>{
	                   console.log("Greska.")	
	                   alert("Podaci su lose uneti.")
	                   window.location.reload()
	               })
           }
           else{
           	  Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li je validna cena (> 99)!'})
           }
        },
        
        scheduleAppointment:function(appointment){
          axios
          .put('/fishingAppointments/scheduleAdventureAppointment/' + appointment.id + "/" + this.activeUser.id, {},{
            headers: {
              'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
            },})
          .then(response=>{
              window.location.reload()
          })
          .catch(error=>{
              console.log("Greska.")	
              alert("Podaci su lose uneti.")
              //window.location.reload()

          })
        },
        checkUserandPenalties(){
          if(this.activeUser.role == 'client'){
              let num = 0
              axios
              .get('/reports/findAllReportsByClient/' + this.activeUser.id)
              .then(response=>{
                  num = response.data.cottageReports.length + response.data.boatReports.length + response.data.fishingReports.length;
                  if(num < 3)
                      document.getElementById("scheduleButton").disabled = false
                  else
                      document.getElementById("scheduleButton").disabled = true
              })
              .catch(error=>{
                  console.log("Greska.")	
                  alert("Podaci su lose uneti.")
              })
              return true;
          }else return false;

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
        	this.quickAppointment.timeUntil !== "" && this.quickAppointment.extraNotes !== "" && this.quickAppointment.price !== "" &&
        	this.quickAppointment.price > 99)
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
            this.quickAppointment.extraNotes = ""
    	},
    	clearNewPricelistItem(){
    		this.newPricelistItem.description = ""
    		this.newPricelistItem.price = 100
    	},
        subscribe:function(){
            const subscribeAdventure = {
                id: 0,
                fishingAdventure: this.adventure,
                client: this.activeUser
            }
            axios
            .post('/subscribe/subscribeAdventure', subscribeAdventure,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()
            })
        },
        unsubscribe:function(){
            const subscribeAdventure = {
                id: 0,
                fishingAdventure: this.adventure,
                client: this.activeUser
            }
            axios
            .post('/subscribe/unsubscribeAdventure', subscribeAdventure,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()
            })
        },
        exist:function(){
            var postoji = false
            for(var i = 0; i < this.subscibedAdventures.length; i++){
                if(this.activeUser.id == this.subscibedAdventures[i].client.id && this.adventure.id == this.subscibedAdventures[i].fishingAdventure.id){
                    postoji = true
                    break
                }
            }
            return postoji
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
            
        axios.all([
                axios.get("fishingAdventures/getSelectedAdventure/" + this.$route.query.id), 
                axios.get("fishingAppointments/getQuickFishingAppointments/" + this.$route.query.id),
                axios.get("pricelist/getInstructorsPricelist/" + this.activeUser.id),
                axios.get('/subscribe/getAllSubscibedAdventures')]).then(axios.spread((...responses) => {
            this.adventure = responses[0].data
            this.appointments = responses[1].data
            this.pricelist = responses[2].data
            this.subscibedAdventures = responses[3].data
        }))
    },

});