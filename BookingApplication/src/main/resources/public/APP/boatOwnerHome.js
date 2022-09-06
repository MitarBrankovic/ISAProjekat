Vue.component("BoatOwnerHome", {
    data: function() {
        return {        
			activeUser:"",
            boats:[],
			newBoat:{name:"",longitude:0,latitude:0,boatType:"",lenght:0,enginePower:0,engineNumber:"",maxSpeed:0,navigationEquipment:"",address:"",description:"",capacity:1,rules:"",fishingEquipment:"",priceList:"",pricePerHour:0,shipOwnerId:"",maxAmountOfPeople:1},
			editBoat:{id:0,name:"",longitude:0,latitude:0,boatType:"",lenght:0,enginePower:0,engineNumber:"",maxSpeed:0,navigationEquipment:"",address:"",description:"",capacity:1,rules:"",fishingEquipment:"",priceList:"",pricePerHour:0,shipOwnerId:"",maxAmountOfPeople:1},

	 }
    },

template: ` 
	<div>
           <searchOwnerBoat id="search" @clicked="searchOwnerBoats"></searchOwnerBoat>
			<div clas="row" style="margin-top:1%">
				<div class="col">
					<button type="button" data-bs-toggle="modal" data-bs-target="#newBoat" class="btn btn-sm btn-success">Dodaj novi brod</button>
				</div>
				<div class="col">
				</div>
			</div>
            <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <div class="col"  v-for = "b in boats">
                        <div class="card" style="width: 93%">
                            <img src="../images/ship.png" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{b.name}}</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><b>Opis:</b> {{b.description}}</li>
                                <li class="list-group-item"><b>Adresa:</b> {{b.address}}</li>
                                <li class="list-group-item"><b>Vlasnik:</b> {{b.shipOwner.name}} {{b.shipOwner.surname}}</li>
                                <li class="list-group-item"><b>Cena:</b> {{b.pricePerHour}}din/h</li>
                                <li class="list-group-item"><b>Ocena:</b> {{b.rating}}</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" v-on:click="showBoatInformation(b.id)" class="btn btn-secondary">Info</button>
                                <button style="margin-left: 8%;" type="button"  v-on:click="getBoat(b.id)"   data-bs-toggle="modal" data-bs-target="#Boat" class="btn btn-primary">Izmeni</button>
								 <button style="margin-left: 16%;" v-if="activeUser != null"  type="button"  v-on:click="removeBoat(b.id)" class="btn btn-danger">Obrisi</button>
                            </div>
                        </div>



                </div>
            </div>
</div>

<div class="modal fade" id="newBoat" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Novi brod</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="newBoat.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="newBoat.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Tip broda:</span>
                <input type="text" id="lastnameInput" v-model="newBoat.boatType" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="newBoat.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="newBoat.rules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="newBoat.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">           
              	<span style="margin-top: 2%;" class="input-group-text">Snaga motora:</span>
                <input type="number" min="1" max="30" v-model="newBoat.enginePower" class="form-control" placeholder="Unesite snagu motora">           
			</div>
              <div class="col-sm-6 my-col">
                
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po danu:</span>
                <input type="number" min="50" step="50" id="lastnameInput" v-model="newBoat.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Kapacitet: </label>
	                  <input id="bedInput" name="bedInput" v-model="newBoat.capacity" class="form-control" placeholder="Unesite broj kreveta">
	                  </div>
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Oprema za pecanje:</label>
	                  <input id="roomInput" name="roomInput" v-model="newBoat.fishingEquipment" class="form-control" placeholder="Oprema za pecanje">
					  </div>
					 </div>
					 <div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Oprema za navigaciju:</label>
	                  <input id="roomInput" name="roomInput" v-model="newBoat.navigationEquipment" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
						<div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Latituda za mapu:</label>
	                  <input id="roomInput" name="roomInput" v-model="newBoat.latitude" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
					<div class="col-sm-6" style="margin-left: -2.5%;">
						<label class="input-group-text" for="inputGroupFile01">longituda za mapu:</label>
	                  <input id="roomInput" name="roomInput" v-model="newBoat.longitude" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
	                 </div>
	                  
	                   
	                   <div class="col-sm-6">
	                  <button type="button" style="margin-left: 10%; margin-top: 25%;" v-on:click="addNewBoat()" class="btn btn-success btn-lg">Dodaj brod</button>
	 				 </div>
    </div>
 </div>
</div>
</div>
              </div>
          </div>
        </div>


<div class="modal fade" id="Boat" tabindex="-1" role="dialog" aria-labelledby="CottageLabel " aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Novi brod</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="editBoat.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="editBoat.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Tip broda:</span>
                <input type="text" id="lastnameInput" v-model="editBoat.boatType" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="editBoat.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="editBoat.rules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="editBoat.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
              </div>
              <div class="col-sm-6 my-col">
                
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po danu:</span>
                <input type="number" min="50" step="50" id="lastnameInput" v-model="editBoat.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Kapacitet: </label>
	                  <input id="bedInput" name="bedInput" v-model="editBoat.capacity" class="form-control" placeholder="Unesite broj kreveta">
	                  </div>
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Oprema za pecanje:</label>
	                  <input id="roomInput" name="roomInput" v-model="editBoat.fishingEquipment" class="form-control" placeholder="Oprema za pecanje">
					  </div>
					 </div>
					 <div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Oprema za navigaciju:</label>
	                  <input id="roomInput" name="roomInput" v-model="editBoat.navigationEquipment" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
						<div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Latituda za mapu:</label>
	                  <input id="roomInput" name="roomInput" v-model="editBoat.latitude" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
					<div class="col-sm-6" style="margin-left: -2.5%;">
						<label class="input-group-text" for="inputGroupFile01">longituda za mapu:</label>
	                  <input id="roomInput" name="roomInput" v-model="editBoat.longitude" class="form-control" placeholder="Oprema za navigaciju">
	                  </div>
	                 </div>
	                   <div class="col-sm-6">
	                  <button type="button" style="margin-left: 10%; margin-top: 25%;" v-on:click="changeBoat()" class="btn btn-success btn-lg">Izmeni brod</button>
	                   </div>
	                   </div>
                  </div>
              </div>
              </div>
            </div>
        </div>
          </div>
        </div>
      </div>
        </div>

`

,
    methods: {
        cottagesFun: function(){
            this.cottagesButton = true
            this.boatsButton = false
            this.adventuresButton = false
        },
        boatsFun: function(){
            this.cottagesButton = false
            this.boatsButton = true
            this.adventuresButton = false
        },
        adventuresFun: function(){
            this.cottagesButton = false
            this.boatsButton = false
            this.adventuresButton = true
        },
        searchOwnerCottages:function(search){
            console.log(search)
            axios
            .post('/cottages/searchOwnerCottages',search)
            .then(response=>{
                this.cottages = response.data
            })
        },
        searchBoats:function(search){
            console.log(search)
            axios
            .post('/boats/searchBoats',search)
            .then(response=>{
                this.boats = response.data
            })
        },
        searchAdventures:function(search){
            console.log(search)
            axios
            .post('/fishingAdventures/searchAdventures',search)
            .then(response=>{
                this.adventures = response.data
            })
        },
		showCottageInformation(id){
			this.$router.push("/profileCottage?id=" + id)
		},
        showBoatInformation(id){
			this.$router.push("/profileBoat?id=" + id)
		},
        showAdventureInformation(id){
			this.$router.push("/selectedFishingAdventure?id=" + id)
		},
		imageAddedNew(e){
            var files = e.target.files;
			PreviewImage();
			if(!files.length)
				return;
			
				this.createImageNew(files[0]);
        },
		addNewBoat() {
        	this.newBoat.shipOwnerId = this.activeUser.id
        	if (this.checkNewBoat()){
        	console.log(this.newBoat)
        		axios
	               .post('/boats/addNewBoat', this.newBoat, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.boats = response.data
	                  this.newBoat.name = ""
	                  this.newBoat.address = ""
	                  this.newBoat.description = ""
	                  this.newBoat.rules = ""
	                  this.newBoat.maxAmountOfPeople = 1
	                  this.newBoat.pricePerHour = 50
	               })

        	}
        	else {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li su validni maksimalni broj osoba (ceo broj > 0) i cena po satu (broj > 49)!'})
        	}
        },
 checkIfRoundNumber() {
        let nes = this.newBoat.maxAmountOfPeople
        let stringnes = nes.toString()
        	if (!stringnes.includes('.') && !stringnes.includes('e')) {
        		console.log(nes)
        		return true;
        		}
        		else {
        		console.log(nes)
        		return false
      	  }
 		},       
checkNewBoat(){
        console.log(this.checkIfRoundNumber())
        	if (this.newBoat.name !== "" && this.newBoat.address !== "" &&  this.newBoat.boatType !== "" && 
        	this.newBoat.description !== "" && this.newBoat.photo !== "" && this.newBoat.photo !== null && this.newBoat.behaviourRules !== "" && 
        	this.newBoat.equipment !== "" && this.newBoat.maxAmountOfPeople !== "" && this.newBoat.pricePerHour !== "" &&
        	 this.newBoat.maxAmountOfPeople > 0 && this.newBoat.pricePerHour > 49)
        	// !this.newBoat.maxAmountOfPeople.includes(".") && !this.editAdventure.maxAmountOfPeople.includes("e")
        		return true;
        	else
        		return false;
        },
async removeBoat(id){
	
	
	var overlap = await this.checkOverlap(id)
	if(overlap){
	axios
	               .post('/boats/removeBoat/'+ id, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.boats = response.data
	               })
}
else {
         	  Swal.fire({icon: 'error', title: 'Greška', text: 'Brod zauzet!'})
           }

},
  async checkOverlap(id) {
        	var bool = false;
        	await axios
               .post('/boatAppointments/checkOverlapNow/' + id)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
        },		
getBoat(id){
	axios
		.get('/boats/getBoatById/' + id,{
			headers:{
				'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
			},})
	               .then(response=>{
	                  this.editBoat = response.data
					  console.log(this.editBoat)
		})
},
 async changeBoat(){
	this.editBoat.shipOwnerId = this.activeUser.id
	var overlap = await this.checkOverlap(this.editBoat.id)
	if(overlap){
	
	axios
	               .post('/boats/editBoat', this.editBoat, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.cottages = response.data
	                  this.editBoat.name = ""
	                  this.editBoat.address = ""
	                  this.editBoat.description = ""
	                  this.editBoat.rules = ""
	                  this.editBoat.maxAmountOfPeople = 1
	                  this.editBoat.pricePerHour = 50
	               })	
        	}
else {
         	  Swal.fire({icon: 'error', title: 'Greška', text: 'Brod zauzet!'})
           }
        },
isAvailable(id){
	axios
	.get('/cottageAppointments/isCottFree/' + id, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.flag = response.data
	               })	
        	
        },
 searchOwnerBoats:function(search){
            console.log(search)
            axios
            .post('/boats/searchOwnerBoats',search)
            .then(response=>{
                this.boats = response.data
            })
        },
    },
    mounted() {
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios.all([axios.get('/boats/getOwnersBoats/' + this.activeUser.id )]).then(axios.spread((...responses) => {
           this.boats = responses[0].data

       }))
    }
});