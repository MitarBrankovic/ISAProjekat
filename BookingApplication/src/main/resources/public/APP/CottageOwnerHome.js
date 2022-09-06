Vue.component("CottageOwnerHome", {
    data: function() {
        return {        
			activeUser:"",
            cottages:[],
			newCottage:{name:"",address:"",description:"",roomsNum:1,bedsNum:1,rules:"",priceList:"",pricePerHour:0,cottageOwnerId:"",maxAmountOfPeople:1},
       		editCottage:{id:0,name:"",address:"",description:"",roomsNum:1,bedsNum:1,rules:"",priceList:"",pricePerHour:"",cottageOwnerId:"",maxAmountOfPeople:1},
			flag:false,
	 }
    },

template: ` 
	<div>
            <searchOwnerCottage id="search" @clicked="searchOwnerCottages"></searchOwnerCottage>
			<div clas="row" style="margin-top:1%">
				<div class="col">
					<button type="button" data-bs-toggle="modal" data-bs-target="#newCottage" class="btn btn-sm btn-success">Dodaj novu vikendicu</button>
				</div>
				<div class="col">
				</div>
			</div>
            <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <div class="col"  v-for = "c in cottages">
                        <div class="card" style="width: 93%">
                            <img src="../images/cottage.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{c.name}}</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">{{c.description}}</li>
                                <li class="list-group-item">{{c.address}}</li>
                                <li class="list-group-item">Num of rooms: {{c.roomsNum}}</li>
                                <li class="list-group-item">Num of beds: {{c.bedsNum}}</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" v-on:click="showCottageInformation(c.id)" class="btn btn-secondary">Info</button>
                                <button style="margin-left: 8%;" type="button"  v-on:click="getCottage(c.id)"   data-bs-toggle="modal" data-bs-target="#Cottage" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" v-on:click="removeCottage(c.id)" class="btn btn-danger">Obrisi</button>
                            </div>
                        </div>



                </div>
            </div>

 </div>
<div class="modal fade" id="newCottage" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Nova vikendica</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="newCottage.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="newCottage.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
                <input type="text" id="lastnameInput" v-model="newCottage.city" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="newCottage.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="newCottage.rules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="newCottage.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
                <select>
                  <option value="0" selected>Besplatno</option>
                  <option value="10">10%</option>
                  <option value="20">20%</option>
                  <option value="30">30%</option>
                  <option value="40">40%</option>
                  <option value="50">50%</option>
                </select>
              </div>
              <div class="col-sm-6 my-col">
                
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po danu:</span>
                <input type="number" min="50" step="50" id="lastnameInput" v-model="newCottage.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Broj kreveta:</label>
	                  <input id="bedInput" name="bedInput" v-model="newCottage.bedsNum" class="form-control" placeholder="Unesite broj kreveta">
	                  </div>
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Broj soba:</label>
	                  <input id="roomInput" name="roomInput" v-model="newCottage.roomsNum" class="form-control" placeholder="Unesite broj soba">
					  </div>
						<div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Dodavanje slike:</label>
	                  <input id="uploadImage" name="myPhoto" required @change=imageAddedNew type="file" accept="image/png, image/jpeg" class="form-control">
	                  </div>
	                  </div>
	                  <div class="row" style="margin-top: 5%;">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <img  id="uploadPreview" style="width: 375px; height: 260px;" />
	                   </div>
	                   <div class="col-sm-6">
	                  <button type="button" style="margin-left: 10%; margin-top: 25%;" v-on:click="addNewCottage()" class="btn btn-success btn-lg">Dodaj vikendicu</button>
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
<div class="modal fade" id="Cottage" tabindex="-1" role="dialog" aria-labelledby="CottageLabel " aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="CottageLabel">Nova vikendica</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="editCottage.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="editCottage.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
                <input type="text" id="lastnameInput" v-model="editCottage.city" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="editCottage.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="editCottage.rules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="editCottage.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
                <select>
                  <option value="0" selected>Besplatno</option>
                  <option value="10">10%</option>
                  <option value="20">20%</option>
                  <option value="30">30%</option>
                  <option value="40">40%</option>
                  <option value="50">50%</option>
                </select>
              </div>
              <div class="col-sm-6 my-col">
                
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po danu:</span>
                <input type="number" min="50" step="50" id="lastnameInput" v-model="editCottage.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Broj kreveta:</label>
	                  <input id="bedInput" name="bedInput" v-model="editCottage.bedsNum" class="form-control" placeholder="Unesite broj kreveta">
	                  </div>
					  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Broj soba:</label>
	                  <input id="roomInput" name="roomInput" v-model="editCottage.roomsNum" class="form-control" placeholder="Unesite broj soba">
					  </div>
					  <div class="row">
					  <div class="col-sm-6" style="margin-left: -2.5%;">	                 
					  </div>
						<div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Dodavanje slike:</label>
	                  <input id="uploadImage" name="myPhoto" required @change=imageAddedNew type="file" accept="image/png, image/jpeg" class="form-control">
	                  </div>
	                  </div>
	                  <div class="row" style="margin-top: 5%;">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <img  id="uploadPreview" style="width: 375px; height: 260px;" />
	                   </div>
	                   <div class="col-sm-6">
	                  <button type="button"  style="margin-left: 10%; margin-top: 25%;" v-on:click="changeCottage()" class="btn btn-success btn-lg">Sacuvaj izmene</button>
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
		addNewCottage() {
        	this.newCottage.cottageOwnerId = this.activeUser.id
        	if (this.checkNewCottage()){
        	console.log(this.newCottage)
        		axios
	               .post('/cottages/addNewCottage', this.newCottage, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.cottages = response.data
	                  this.newCottage.name = ""
	                  this.newCottage.address = ""
	                  this.newCottage.city = ""
	                  this.newCottage.description = ""
	                  this.newCottage.rules = ""
	                  this.newCottage.maxAmountOfPeople = 1
	                  this.newCottage.pricePerHour = 50
	               })

        	}
        	else {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li su validni maksimalni broj osoba (ceo broj > 0) i cena po satu (broj > 49)!'})
        	}
        },
 checkIfRoundNumber() {
        let nes = this.newCottage.maxAmountOfPeople
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
checkNewCottage(){
        console.log(this.checkIfRoundNumber())
        	if (this.newCottage.name !== "" && this.newCottage.address !== "" &&  this.newCottage.city !== "" && 
        	this.newCottage.description !== "" && this.newCottage.photo !== "" && this.newCottage.photo !== null && this.newCottage.behaviourRules !== "" && 
        	this.newCottage.equipment !== "" && this.newCottage.maxAmountOfPeople !== "" && this.newCottage.pricePerHour !== "" &&
        	this.newCottage.cancellingPrecentage !== "" && this.newCottage.maxAmountOfPeople > 0 && this.newCottage.pricePerHour > 49)
        	// !this.newCottage.maxAmountOfPeople.includes(".") && !this.editAdventure.maxAmountOfPeople.includes("e")
        		return true;
        	else
        		return false;
        },
async removeCottage(id){
		var overlap = await this.checkOverlap(id)
	if(overlap){
	axios
	               .post('/cottages/removeCottage/'+ id, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.cottages = response.data
	               })
}
 else {
         	  Swal.fire({icon: 'error', title: 'Greška', text: 'Vikendica zauzeta!'})
           }
},
		
getCottage(id){
	axios
		.get('/cottages/getCottageById/' + id,{
			headers:{
				'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
			},})
	               .then(response=>{
	                  this.editCottage = response.data
					  console.log(this.editCottage)
		})
},
  async changeCottage(){
	this.editCottage.cottageOwnerId = this.activeUser.id
	var overlap = await this.checkOverlap(this.editCottage.id)
	if(overlap){
	axios
	               .post('/cottages/editCottage', this.editCottage, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	               .then(response=>{
	                  this.cottages = response.data
	                  this.editCottage.name = ""
	                  this.editCottage.address = ""
	                  this.editCottage.city = ""
	                  this.editCottage.description = ""
	                  this.editCottage.rules = ""
	                  this.editCottage.maxAmountOfPeople = 1
	                  this.editCottage.pricePerHour = 50
	               })	
        	}
 else {
         	  Swal.fire({icon: 'error', title: 'Greška', text: 'Vikendica zauzeta!'})
           }
        },
  async checkOverlap(id) {
        	var bool = false;
        	await axios
               .post('/cottageAppointments/checkOverlapNow/' + id)
               .then((response)=>{
               		bool = response.data
               		console.log(bool)
               		return response.data
                })
          return bool
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
    },
    mounted() {
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios.all([axios.get('/cottages/getAllCottagesByOwner/' + this.activeUser.id )]).then(axios.spread((...responses) => {
           this.cottages = responses[0].data

       }))
    }
});