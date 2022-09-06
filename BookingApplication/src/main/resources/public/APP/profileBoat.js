Vue.component("ProfileBoat", {
    data: function() {
        return {
            activeUser: "",
            boat : "",
			address:"",
			previewMap:false,
			appointments : [],
            subscibedBoats:[],
            quickAppointment: {dateFrom: "", timeFrom: "8:00", dateUntil: "", timeUntil: "12:00", extraNotes: "", price: 100, boatId: 0 },
			 photos: "",
			newPhoto:{photo: null, entityId: 0},
			pricelist: "",
            newPricelistItem: {instructorsId: "", description: "", price: 50},
            removeItemObject: {itemId: "", instructorId: ""},
        }
    },
    template : ` 
<div>
	<div class="row">
    <div class="col col-sm-6">
  	
    <h1>{{boat.name}}</h1>
    <p>Adress: {{boat.address}}
    <br> Type of boat : {{boat.boatType}}
    <br> Length of boat: {{boat.length}}
    <br> Engine number : {{boat.engineNumber}}
    <br> Engine power : {{boat.enginePower}}
    <br> Maximum boat speed: {{boat.maxSpeed}}
    <br> Navigation equipment : {{boat.navigationEquipment}}
    <br> Description : {{boat.description}}
    <br> Capacity :{{boat.capacity}}
    <br> Rules : {{boat.rules}}
    <br> Fising equipment : {{boat.fishingEquipment}}
    <br> Price list : {{boat.priceList}}
    <br> Cancelation terms : {{boat.cancellationTerms}}
    </p><br>
    <button v-if="activeUser.role == 'client' && !exist()" type="submit" class="button" v-on:click="subscribe()">Pretplati se</button>
    <button v-if="activeUser.role == 'client' && exist()" type="submit" class="btn btn-danger" v-on:click="unsubscribe()">Odjavi se</button>
</div>
	<div class="col col-sm-6">
                        <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
						  <div class="carousel-inner">
						  <div class="carousel-item active">
						      <img :src="boat.photo" class="entityPhoto" alt="...">
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
				                	<input id="uploadImage" v-if="activeUser != null && activeUser.role == 'ship_owner'"  name="myPhoto" required @change=imageAddedNew type="file" accept="image/png, image/jpeg" class="form-control">
		                      	</div>
		                      	<div class="col col-sm-3">
		                      		<button type="button" v-if="activeUser != null && activeUser.role == 'ship_owner' " style="margin-top: 15%" v-on:click="addPhoto()" class="btn btn-success">Dodaj sliku</button>
		                      	</div>
		                      	<div class="col col-sm-3">
		                      		<button type="button" v-if="activeUser != null && activeUser.role == 'ship_owner' " style="margin-top: 15%" v-on:click="removePhoto()" class="btn btn-danger">Obriši sliku</button>
		                      	</div>
	                      	</div>
                      	</div>   
                      	</div>    
    <br><br><hr>

    <table id="tabela" class="table table-success table-striped table-sm table-bordered">
        <thead>
        <tr>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="a in appointments">
            <td>{{a.appointmentStart}}</td>
            <td>{{a.duration}} sati</td>
            <td>{{a.maxAmountOfPeople}}</td>
            <td>{{a.extraNotes}}</td>
            <td>{{a.price}}</td>
            <td v-if="a.client==null"><button id="scheduleButton" v-if="checkUserandPenalties()" type="button" class="btn btn-success" v-on:click="scheduleAppointment(a)">Zakazi</button> </td>
            <td v-else><p style="color:red;">Zakazano</p> </td>
            </tr>
        </tbody>
    </table>

    <button type="button" style="margin-top: 3%; margin-bottom: 3%;" v-if="activeUser != null && activeUser.role == 'ship_owner'" data-bs-toggle="modal" data-bs-target="#newAppointment" class="btn btn-danger btn-lg">Dodaj brzu rezervaciju</button>
<div>
	<div>
	<button type="button" class="btn btn-outline-success"   v-on:click="previewMapChooseLocation()"><i></i>See on map</button>
                          </div>
			                
			                <div class="col">
			                 <div id="popup" class="ol-popup">
					            <a href="#" id="popup-closer" class="ol-popup-closer"></a>
					            <div id="popup-content"></div>
          					  </div>
								
           						 <div id="map" class="map" v-if="previewMap" style="width: 1000px;height:500px; margin-right:50px;margin-top:20px"></div>
        					</div>
        					
        				 </div>
<h2 v-if="activeUser != null && activeUser.role == 'ship_owner'">Cenovnik dodatnih usluga</h2>
	
	<div v-if="activeUser != null && activeUser.role == 'ship_owner'" class="container-fluid" style="margin-top: 3%">
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
		            <td><button v-if="activeUser.id == boat.shipOwner.id" type="button" data-bs-toggle="modal" data-bs-target="#areYouSure" v-on:click="prepareItemToRemove(p.id)" class="btn btn-danger">Obriši</button> </td>
	            </tr>
	            <tr v-if="activeUser.id == boat.shipOwner.id">
		            <td><input type="text" class="form-control" v-model="newPricelistItem.description" placeholder="Unesite opis..."></td>
		            <td><input type="number" min = "50" step="50" class="form-control" v-model="newPricelistItem.price" placeholder="Unesite cenu..."></td>
		            <td><button type="button" v-on:click="addPricelistItem()" class="btn btn-success">Dodaj novu uslugu</button> </td>
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
    	`
    	,
    methods: {
        scheduleAppointment:function(appointment){
            axios
            .put('/boatAppointments/scheduleBoatAppointment/' + appointment.id + "/" + this.activeUser.id, {},{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{	
                //alert("Podaci su lose uneti.")
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
        subscribe:function(){
            const subscibedBoat = {
                id: 0,
                boat: this.boat,
                client: this.activeUser
            }
            axios
            .post('/subscribe/subscribeBoat', subscibedBoat,{
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
            const subscibedBoat = {
                id: 0,
                boat: this.boat,
                client: this.activeUser
            }
            axios
            .post('/subscribe/unsubscribeBoat', subscibedBoat,{
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
            for(var i = 0; i < this.subscibedBoats.length; i++){
                if(this.activeUser.id == this.subscibedBoats[i].client.id && this.boat.id == this.subscibedBoats[i].boat.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
    async checkOverlap() {
        	var bool = false;
        	await axios
               .post('/boatAppointments/checkOverlap', this.quickAppointment)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },
checkNewAppointment(){
        	if (this.quickAppointment.dateFrom !== "" && this.quickAppointment.timeFrom !== "" &&  this.quickAppointment.dateUntil !== "" && 
        	this.quickAppointment.timeUntil !== "" && this.quickAppointment.extraNotes !== "" && this.quickAppointment.price !== "" &&
        	this.quickAppointment.price > 99)
        		return true;
        	else
        		return false;
        },
async addNewAppointment(){
            this.quickAppointment.boatId = this.boat.id
            if (this.checkNewAppointment()){
            var overlap = await this.checkOverlap()
            	if (this.checkDate()){
            		if (overlap) {
	            		if (1==1) {
			         	axios
			               .post('/boatAppointments/addQuickAppointment', this.quickAppointment, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
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
  checkDate() {
        	var timeFrom = this.quickAppointment.timeFrom.split(":")[0]
        	var timeUntil = this.quickAppointment.timeUntil.split(":")[0]
        	var DateFrom = new Date(this.quickAppointment.dateFrom.substring(0,4), this.quickAppointment.dateFrom.substring(5,7), this.quickAppointment.dateFrom.substring(8,10), timeFrom, 0)
			var DateUntil = new Date(this.quickAppointment.dateUntil.substring(0,4), this.quickAppointment.dateUntil.substring(5,7), this.quickAppointment.dateUntil.substring(8,10), timeUntil, 0)
        	console.log(DateFrom)
        	console.log(DateUntil)
        	return DateUntil > DateFrom       	
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
addPhoto(){
        this.newPhoto.entityId = this.boat.id;
        if (this.newPhoto.photo != null){
         	axios
               .post('/boats/addPhoto', this.newPhoto, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
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
               .post('/boats/removePhoto/' + this.boat.id, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
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
               .post('/pricelist/addPricelistItem', this.newPricelistItem, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
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
               .post('/pricelist/deletePricelistItem', this.removeItemObject, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
               .then(response=>{
                  this.pricelist = response.data
               })
               
        },
checkNewPricelistItem(){
        	if (this.newPricelistItem.description !== "" && this.newPricelistItem.price !== "" && this.newPricelistItem.price > 49) 
        		return true;
        	else
        		return false;
        },
init: function(){
            const map = new ol.Map({
                target: 'map',
                layers: [
                  new ol.layer.Tile({
                    source: new ol.source.OSM()
                  })
                ],
                view: new ol.View({
                  center: ol.proj.fromLonLat([this.boat.longitude, this.boat.latitude]),
                  maxZoom: 18,
                  zoom: 12
                })
              })

              var layer = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [
                        new ol.Feature({
                            geometry: new ol.geom.Point(ol.proj.fromLonLat([this.boat.longitude, this.boat.latitude]))
                        })
                    ]
                })
            });
            map.addLayer(layer);

            var container = document.getElementById('popup');
            var content = document.getElementById('popup-content');
            var closer = document.getElementById('popup-closer');
  
            var overlay = new ol.Overlay({
                element: container,
                autoPan: true,
                autoPanAnimation: {
                    duration: 250
                }
            });
            map.addOverlay(overlay);
  			console.log(this.boat)
            closer.onclick = function() {
                overlay.setPosition(undefined);
                closer.blur();
                return false;
            };
  
            map.on('singleclick', function (event) {
              if (map.hasFeatureAtPixel(event.pixel) === true) {
                  var coordinate = event.coordinate;
  
                  content.innerHTML =  this.boat.name;
                  overlay.setPosition(coordinate);
              } else {
                  overlay.setPosition(undefined);
                  closer.blur();
              }
          });
  
          content.innerHTML = this.boat.name;
          overlay.setPosition(ol.proj.fromLonLat([this.boat.longitude, this.boat.latitude]));

        },
        previewMapChooseLocation: function () {
            this.previewMap = !this.previewMap;
            if (this.previewMap) {
                // Draw map on screen
                this.$nextTick(function () {
                    this.init();
    
                    // Seting some extra style for map
                    let c = document.getElementById("map").childNodes;
                    c[0].style.borderRadius  = '10px';
                    c[0].style.border = '4px solid lightgrey';
                })
            }
          }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))

        axios.all([
            axios.get("boats/getSelectedBoat/" + this.$route.query.id), 
 			axios.get("boats/getBoatPhotos/" + this.$route.query.id),
            axios.get("boatAppointments/getAllQuickAppointments/" + this.$route.query.id),
			axios.get("pricelist/getInstructorsPricelist/" + this.activeUser.id),
            axios.get('/subscribe/getAllSubscibedBoats')]).then(axios.spread((...responses) => {
            this.boat = responses[0].data
 			this.photos = responses[1].data
            this.appointments = responses[2].data
			this.pricelist = responses[3].data
            this.subscibedBoats = responses[4].data
this.$nextTick(function () {
            this.init();
            this.previewMap = true;
            this.previewMapChooseLocation();
        })
        }))
    },

});