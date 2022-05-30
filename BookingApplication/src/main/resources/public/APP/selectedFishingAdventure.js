Vue.component("SelectedFishingAdventure", {
    data: function() {
        return {
            activeUser:null,
            adventure: "",
            photos: "",
            pricelistIdRemove: "",
            adventureIdRemove: "",
            userId:"",
            quickAppointment: {dateFrom: "", timeFrom: "8:00", dateUntil: "", timeUntil: "12:00", extraNotes: "", price: 100, adventureId: 0 },
            appointments: "",
            pricelist: "",
            newPricelistItem: {instructorsId: "", description: "", price: 50},
            removeItemObject: {itemId: "", instructorId: ""},
            subscibedAdventures:[],
            newPhoto:{photo: null, entityId: 0}
        }
    },
    template :`
    <div>
    <div class="container-fluid" style="margin-top: 3%">
    <div class="row my-row" style="margin-top: 2%;">
                      <div class="col col-sm-6">
                        <h1 style="color: #5cb85c;">{{adventure.name}}</h1>
                        <table class="table">
					        <tbody>
					        <tr>
					            <td> Adresa: {{adventure.address}}, {{ adventure.city}}</td>
					        </tr>
					        <tr>
					            <td> Opis : {{adventure.description}}</td>
					        </tr>
					        <tr>
					            <td> Kapacitet : {{adventure.maxAmountOfPeople}} osoba</td>
					        </tr>
					        <tr>
					            <td> Pravila : {{adventure.behaviourRules}}</td>
					        </tr>
					        <tr>
					            <td> Oprema : {{adventure.equipment}}</td>
					        </tr>
					        <tr>
					            <td> Cena po satu : {{adventure.pricePerHour}} din/h</td>
					        </tr>
					        <tr>
					            <td> Procenat za otkazivanje : {{adventure.cancellingPrecentage}} %</td>
					        </tr>
					        <tr>
					            <td> Ocena : {{adventure.rating}}/5</td>
					        </tr>
					        </tbody>
					    </table>
                      </div>
     				  <div class="col col-sm-6">
                        <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
						  <div class="carousel-inner">
						  <div class="carousel-item active">
						      <img :src="adventure.photo" class="entityPhoto" alt="...">
						    </div>
						    <div v-for="p in photos" class="carousel-item">
						      <img :src="p.photo" class="entityPhoto" alt="...">
						    </div>
						  </div>
						  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
						    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
						    <span class="visually-hidden">Previous</span>
						  </button>
						  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
						    <span class="carousel-control-next-icon" aria-hidden="true"></span>
						    <span class="visually-hidden">Next</span>
						  </button>
						</div>
                      </div>       
     </div>
 		<div class="row my-row">
    					<div class="col col-sm-4">
    					</div> 
                     	<div class="col col-sm-4">
                     	</div>
                      	<div class="col col-sm-4">
	                      	<div class="row my-row">
		                      	<div class="col col-sm-6">
		                      		<label v-if="activeUser != null && activeUser.role == 'fishing_instructor' && activeUser.id == adventure.fishingInstructor.id" class="input-group-text" for="inputGroupFile01">Dodavanje slike:</label>
				                	<input id="uploadImage" v-if="activeUser != null && activeUser.role == 'fishing_instructor'  && activeUser.id == adventure.fishingInstructor.id" name="myPhoto" required @change=imageAddedNew type="file" accept="image/png, image/jpeg" class="form-control">
		                      	</div>
		                      	<div class="col col-sm-3">
		                      		<button type="button" v-if="activeUser != null && activeUser.role == 'fishing_instructor'  && activeUser.id == adventure.fishingInstructor.id" style="margin-top: 15%" v-on:click="addPhoto()" class="btn btn-success">Dodaj sliku</button>
		                      	</div>
		                      	<div class="col col-sm-3">
		                      		<button type="button" v-if="activeUser != null && activeUser.role == 'fishing_instructor' && activeUser.id == adventure.fishingInstructor.id" style="margin-top: 15%" v-on:click="removePhoto()" class="btn btn-danger">Obriši sliku</button>
		                      	</div>
	                      	</div>
                      	</div>   
                      	</div>    
	    				</div>

    <button v-if="activeUser != null && activeUser.role == 'client' && !exist()" type="submit" class="button" v-on:click="subscribe()">Pretplati se</button>
    <button v-if="activeUser != null && activeUser.role == 'client' && exist()" type="submit" class="btn btn-danger" v-on:click="unsubscribe()">Odjavi se</button>

    <br><br><hr>
    <h2 style="color: #5cb85c;">Brze rezervacije</h2>
    <div class="container-fluid" style="margin-top: 3%">
    <table id="tabela" class="table">
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
            <td v-if="activeUser != null && appointment.client==null && activeUser.role == 'client'"><button id="scheduleButton" v-if="checkUserandPenalties()" type="button" class="btn btn-success" v-on:click="scheduleAppointment(appointment)">Zakazi</button></td>
            <td v-else-if="activeUser != null && appointment.client == null && activeUser.role == 'fishing_instructor'"><p style="color:green;">Slobodno</p></td>
            <td v-else-if="activeUser == null && appointment.client == null"><p style="color:green;">Slobodno</p></td>
            <td v-else><p style="color:red;">Zakazano</p> </td>
            </tr>
        </tbody>
    </table>
    </div>
    <button type="button" style="margin-top: 3%; margin-bottom: 3%;" v-if="activeUser != null && activeUser.role == 'fishing_instructor'  && activeUser.id == adventure.fishingInstructor.id" data-bs-toggle="modal" data-bs-target="#newAppointment" class="btn btn-danger btn-lg">Dodaj brzu rezervaciju</button>
	<h2 v-if="activeUser != null && activeUser.role == 'fishing_instructor'">Cenovnik dodatnih usluga</h2>
	
	<div v-if="activeUser != null && activeUser.role == 'fishing_instructor'" class="container-fluid" style="margin-top: 3%">
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
		            <td><button v-if="activeUser.id == adventure.fishingInstructor.id" type="button" data-bs-toggle="modal" data-bs-target="#areYouSure" v-on:click="prepareItemToRemove(p.id)" class="btn btn-danger">Obriši</button> </td>
	            </tr>
	            <tr v-if="activeUser.id == adventure.fishingInstructor.id">
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
                          <select class="form-select" v-model="quickAppointment.timeFrom" aria-label="Example select with button addon">
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
                        <input type="date" class="form-control" v-model="quickAppointment.dateUntil" placeholder="Datum-do">
                          <span class="input-group-text" id="basic-addon1">Datum-do</span>
                      </div>
                      <div class="col col-sm-6">
                          <select class="form-select" v-model="quickAppointment.timeUntil" aria-label="Example select with button addon">
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
       async addNewAppointment(){
            this.quickAppointment.adventureId = this.adventure.id
            if (this.checkNewAppointment()){
            var overlap = await this.checkOverlap()
    		var instrAvail = await this.checkInstructorsAvailability()
            	if (this.checkDate()){
            		if (overlap) {
	            		if (instrAvail) {
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
           else{
           	  Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li je validna cena (> 99)!'})
           }
        },
        
        async checkOverlap() {
        	var bool = false;
        	await axios
               .post('/fishingAppointments/checkOverlap', this.quickAppointment)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },
        
        async checkInstructorsAvailability() {
        	var bool = false;
        	await axios
               .post('/fishingAppointments/checkInstructorsAvailability', this.quickAppointment)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },
        
        imageAddedNew(e){
            var files = e.target.files;
			if(!files.length)
				return;
			
				this.createImageNew(files[0]);
        },
        createImageNew(file){
			var image = new Image();
            var reader = new FileReader();
			var vm = this;

			reader.onload = (e) =>{
				this.newPhoto.photo = e.target.result;

			};
			reader.readAsDataURL(file);
        },
        
        checkDate() {
        	var timeFrom = this.quickAppointment.timeFrom.split(":")[0]
        	var timeUntil = this.quickAppointment.timeUntil.split(":")[0]
        	var DateFrom = new Date(this.quickAppointment.dateFrom.substring(0,4), this.quickAppointment.dateFrom.substring(5,7), this.quickAppointment.dateFrom.substring(8,10), timeFrom, 0)
			var DateUntil = new Date(this.quickAppointment.dateUntil.substring(0,4), this.quickAppointment.dateUntil.substring(5,7), this.quickAppointment.dateUntil.substring(8,10), timeUntil, 0)
        	console.log(DateFrom)
        	console.log(DateUntil)
        	return DateUntil > DateFrom       	
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
              //console.log("Greska.")	
              alert("Podaci su lose uneti.")
              Swal.fire({
                icon: 'error',
                title: 'Greska...',
                text: 'Neko je vec zakazao termin!',
              })
            setTimeout(location.reload.bind(location), 2000);
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
                        $("#tabela").find("button").prop('disabled', false);
                  else
                        $("#tabela").find("button").prop('disabled', true);
              })
              .catch(error=>{
                  console.log("Greska.")	
                  alert("Podaci su lose uneti.")
              })
              return true;
          }else return false;

        },
        
        addPhoto(){
        this.newPhoto.entityId = this.adventure.id;
        if (this.newPhoto.photo != null){
         	axios
               .post('/fishingAdventures/addPhoto', this.newPhoto)
               .then(response=>{
                  this.photos = response.data
                  console.log(this.photos)
               })
               .catch(error=>{
                   console.log("Greska.")	
                   alert("Podaci su lose uneti.")
                   window.location.reload()
               })
           }
           else {
           	   Swal.fire({icon: 'error', title: 'Greška', text: 'Niste selektovali koju sliku zelite da dodate !'})
           }
        },
        
        removePhoto(){
         	axios
               .post('/fishingAdventure/removePhoto/' + this.adventure.id)
               .then(response=>{
                  this.photos = response.data
                  console.log(this.photos)
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
        if(this.activeUser != null)
        	if (this.activeUser.role != 'fishing_instructor' && this.activeUser.role != 'client' && this.activeUser.role != 'admin')
            	this.$router.push('/')
        if(this.activeUser != null) {
        axios.all([
                axios.get("fishingAdventures/getSelectedAdventure/" + this.$route.query.id), 
                axios.get("fishingAdventures/getAdventuresPhotos/" + this.$route.query.id),
                axios.get("fishingAppointments/getQuickFishingAppointments/" + this.$route.query.id),
                axios.get("pricelist/getInstructorsPricelist/" + this.activeUser.id),
                axios.get('/subscribe/getAllSubscibedAdventures')]).then(axios.spread((...responses) => {
            this.adventure = responses[0].data
            this.photos = responses[1].data
            console.log(this.photos)
            this.appointments = responses[2].data
            this.pricelist = responses[3].data
            this.subscibedAdventures = responses[4].data
        }))
        }
        else{
        	axios.all([
                axios.get("fishingAdventures/getSelectedAdventure/" + this.$route.query.id), 
                axios.get("fishingAdventures/getAdventuresPhotos/" + this.$route.query.id),
                axios.get("fishingAppointments/getQuickFishingAppointments/" + this.$route.query.id)]).then(axios.spread((...responses) => {
            this.adventure = responses[0].data
            this.photos = responses[1].data
            console.log(this.photos)
            this.appointments = responses[2].data
        }))
        }
    },

	
});

