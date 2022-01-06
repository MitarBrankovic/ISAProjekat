Vue.component("FishingInstructorsAdventures", {
    data: function() {
        return {
            activeUser:"",
            instructorsId:"",
            adventureId:"",
            newAdventure : {name: "", address: "", city: "", description: "", photo: "", maxAmountOfPeople: 1, 
            behaviourRules: "", equipment: "", pricePerHour: 50, cancellingPrecentage: 0, instructorsId: ""},
            editAdventure : {name: "", address: "", city: "", description: "", photo: "", maxAmountOfPeople: "", 
            behaviourRules: "", equipment: "", pricePerHour: "", cancellingPrecentage: "", adventureId: ""},
            adventures: "",
            adventureIdRemove: "",
            
        }
    },
    template :`
    <div>
    <div style="margin-top: 2%;" class="container-fluid">
        <div style="margin-left: 8%;" class="row">
          <div class="col col-sm-9">
          </div>
          <div class="col col-sm-2">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#newAdventure">Dodaj novu uslugu</button>
          </div>
          <div class="col col-sm-1">
          </div>
          </div>
          
          <div>
      	     <SearchInstructorsAdventures id="search" @clicked="searchAdventures"></SearchInstructorsAdventures>
	      </div>

		
   <div class="modal fade" id="newAdventure" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Nova avantura</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="newAdventure.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="newAdventure.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
                <input type="text" id="lastnameInput" v-model="newAdventure.city" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="newAdventure.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="newAdventure.behaviourRules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="newAdventure.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
                <span class="input-group-text" style="margin-top: 2%;" id="basic-addon1">Uslovi otkazivanja:</span>
                <select class="form-select" id="managerComboBox" v-model="newAdventure.cancellingPrecentage" aria-label="Example select with button addon">
                  <option value="0" selected>Besplatno</option>
                  <option value="10">10%</option>
                  <option value="20">20%</option>
                  <option value="30">30%</option>
                  <option value="40">40%</option>
                  <option value="50">50%</option>
                </select>
              </div>
              <div class="col-sm-6 my-col">
                <span class="input-group-text" style="margin-top: 2%;">Oprema:</span>
                <textarea class="form-control" id="usernameInput" rows="5" v-model="newAdventure.equipment" placeholder="Unesite opremu..."></textarea>
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po satu:</span>
                <input type="number" min="50" step="50" id="lastnameInput" v-model="newAdventure.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Izbor slike:</label>
	                  <input id="uploadImage" name="myPhoto" required @change=imageAddedNew type="file" accept="image/png, image/jpeg" class="form-control">
	                  </div>
	                  </div>
	                  <div class="row" style="margin-top: 5%;">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <img  id="uploadPreview" style="width: 375px; height: 260px;" />
	                   </div>
	                   <div class="col-sm-6">
	                  <button type="button" style="margin-left: 10%; margin-top: 25%;" v-on:click="addNewAdventure()" class="btn btn-success btn-lg">Dodaj uslugu</button>
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
      
      <div class="modal fade" id="editAdventure" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Izmena avanture</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" v-model="editAdventure.name" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
                <input type="text" id="lastnameInput" v-model="editAdventure.address" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
                <input type="text" id="lastnameInput" v-model="editAdventure.city" class="form-control" placeholder="Unesite grad...">
                <span class="input-group-text" style="margin-top: 2%;">Opis:</span>
                <textarea class="form-control" v-model="editAdventure.description" id="usernameInput" rows="" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 2%;">Pravila ponasanja:</span>
                <textarea class="form-control" v-model="editAdventure.behaviourRules" id="usernameInput" rows="3" placeholder="Unesite pravila ponasanja..."></textarea>
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" v-model="editAdventure.maxAmountOfPeople" class="form-control" placeholder="Unesite maksimalni broj osoba...">
                <span class="input-group-text" style="margin-top: 2%;" id="basic-addon1">Uslovi otkazivanja:</span>
                <select class="form-select" id="managerComboBox" v-model="editAdventure.cancellingPrecentage" aria-label="Example select with button addon">
                  <option value="0" selected>Besplatno</option>
                  <option value="10">10%</option>
                  <option value="20">20%</option>
                  <option value="30">30%</option>
                  <option value="40">40%</option>
                  <option value="50">50%</option>
                </select>
              </div>
              <div class="col-sm-6 my-col">
                <span class="input-group-text" style="margin-top: 2%;">Oprema:</span>
                <textarea class="form-control" rows="5" v-model="editAdventure.equipment" placeholder="Unesite opremu..."></textarea>
                <span class="input-group-text" style="margin-top: 1.6%;">Cena po satu:</span>
                <input type="number" min="50" step="50" v-model="editAdventure.pricePerHour" class="form-control" placeholder="Unesite cenu po satu...">
                <div style="margin-top: 2%;" class="col-sm-12 my-col">
	                  <div class="row">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <label class="input-group-text" for="inputGroupFile01">Izbor slike:</label>
	                  <input id="uploadImageEdit" name="myPhotoEdit" required @change=imageAdded type="file" accept="image/png, image/jpeg" class="form-control">
	                  </div>
	                  </div>
	                  <div class="row" style="margin-top: 5%;">
	                  <div class="col-sm-6" style="margin-left: -2.5%;">
	                  <img  id="uploadPreviewEdit" style="width: 375px; height: 260px;" />
	                   </div>
	                   <div class="col-sm-6">
	                  <button type="button" style="margin-left: 10%; margin-top: 25%;" v-on:click="editSelectedAdventure()" class="btn btn-success btn-lg">Izmeni uslugu</button>
	                   </div>
	                   </div>
                  </div>
              </div>
              </div>
            </div>
        </div>
          </div>
        </div>

        <div class="container-fluid" style="margin-top: 2%; margin-left: 4mm;">
            <div class="row row-cols-1 row-cols-md-4 g-4">
              <div class="col" v-for="adventure in adventures">
                <div class="card" style="width: 93%">
                <!--<img :src="data:image/png;base64,{{adventure.photo.substring()}}" width = "100px" heigth = "200">-->
                  <!--<img :src="_arrayBufferToBase64(adventure.photo)" height="220" class="card-img-top" alt="...">-->
                  <div class="card-body">
                    <h5 class="card-title">{{ adventure.name }}</h5>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">{{ adventure.description }}</li>
                    <li class="list-group-item">{{ adventure.address }}, {{ adventure.city }}</li>
                    <li class="list-group-item">{{ adventure.maxAmountOfPeople }} osoba/e</li>
                    <li class="list-group-item">{{ adventure.rating }} /5</li>
                  </ul>
                  <div class="card-body">
                    <button style="margin-left: 2%;" type="button" v-on:click="getAdventureInfo(adventure.id)" class="btn btn-secondary">Informacije</button>
                    <button style="margin-left: 8%;" type="button" data-bs-toggle="modal" data-bs-target="#editAdventure" v-on:click="fillEditModal(adventure.id)" class="btn btn-primary">Izmeni</button>
                    <button style="margin-left: 8%;" type="button" data-bs-toggle="modal" data-bs-target="#areYouSure" v-on:click="prepareAdventureToRemove(adventure.id)" class="btn btn-danger">Obriši</button>
                  </div>
                </div>
              </div>
            </div>
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
	        <button type="button" class="btn btn-danger" v-on:click="removeAdventure()" data-bs-dismiss="modal" >Da</button>
	      </div>
	    </div>
	  </div>
	</div>
    
    </div>
    	`
    	,
    methods: {
    	getAdventureInfo(id){
            this.adventureId = id;
            this.$router.push({ path: '/selectedFishingAdventure', query: { id: this.adventureId }});
        },
        
    	searchAdventures:function(search){
            console.log(search)
            axios
            .post('/fishingAdventures/searchInstructorsAdventures',search)
            .then(response=>{
                this.adventures = response.data
            })
        },
        
    	removeAdventure() {
    		axios
               .post('/fishingAdventures/checkAdventureRemoval/' + this.adventureIdRemove)
               .then(response=>{
                  if (response.data) {
                  	 axios
	                 .post('/fishingAdventures/removeAdventure/' + this.adventureIdRemove)
	                 .then(response => { this.adventures = response.data 
	                 console.log(response.data)})
                  }
                  else {
                  	Swal.fire({icon: 'error', title: 'Ne možete obrisati ovu avanturu, jer postoje zakazani termini za nju !'})
                  }
               })
    	},
    	
        addNewAdventure() {
        	this.newAdventure.instructorsId = instructorsId
        	if (this.checkNewAdventure()){
        	console.log(this.newAdventure)
        		axios
	               .post('/fishingAdventures/addNewAdventure', this.newAdventure)
	               .then(response=>{
	                  this.adventures = response.data
	                  this.newAdventure.name = ""
	                  this.newAdventure.address = ""
	                  this.newAdventure.city = ""
	                  this.newAdventure.description = ""
	                  this.newAdventure.behaviourRules = ""
	                  this.newAdventure.equipment = ""
	                  this.newAdventure.maxAmountOfPeople = 1
	                  this.newAdventure.pricePerHour = 50
	               })

        	}
        	else {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li su validni maksimalni broj osoba (ceo broj > 0) i cena po satu (broj > 49)!'})
        	}
        },
        checkNewAdventure(){
        console.log(this.checkIfRoundNumber())
        	if (this.newAdventure.name !== "" && this.newAdventure.address !== "" &&  this.newAdventure.city !== "" && 
        	this.newAdventure.description !== "" && this.newAdventure.photo !== "" && this.newAdventure.photo !== null && this.newAdventure.behaviourRules !== "" && 
        	this.newAdventure.equipment !== "" && this.newAdventure.maxAmountOfPeople !== "" && this.newAdventure.pricePerHour !== "" &&
        	this.newAdventure.cancellingPrecentage !== "" && this.newAdventure.maxAmountOfPeople > 0 && this.newAdventure.pricePerHour > 49)
        	// !this.newAdventure.maxAmountOfPeople.includes(".") && !this.editAdventure.maxAmountOfPeople.includes("e")
        		return true;
        	else
        		return false;
        },
       
        checkIfRoundNumber() {
        let nes = this.editAdventure.maxAmountOfPeople
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
        checkEditAdventure(){
        	if (this.editAdventure.name !== "" && this.editAdventure.address !== "" &&  this.editAdventure.city !== "" && 
        	this.editAdventure.description !== "" && this.editAdventure.photo !== null && this.editAdventure.photo !== "" && this.editAdventure.behaviourRules !== "" && 
        	this.editAdventure.equipment !== "" && this.editAdventure.maxAmountOfPeople !== "" && this.editAdventure.pricePerHour !== "" &&
        	this.editAdventure.cancellingPrecentage !== "" && this.editAdventure.maxAmountOfPeople > 0 && this.editAdventure.pricePerHour > 49 && this.checkIfRoundNumber())
        		return true;
        	else
        		return false;
        },
        
        prepareAdventureToRemove(id){
        	this.adventureIdRemove = id;		
        },
        
        fillEditModal(id) {
        	for (const adv in this.adventures) {
        		if (this.adventures[adv].id == id){
        			this.editAdventure.name = this.adventures[adv].name;
        			this.editAdventure.address = this.adventures[adv].address;
        			this.editAdventure.city = this.adventures[adv].city;
        			this.editAdventure.description = this.adventures[adv].description;
        			this.editAdventure.photo = this.adventures[adv].photo;
        			this.editAdventure.maxAmountOfPeople = this.adventures[adv].maxAmountOfPeople;
        			this.editAdventure.behaviourRules = this.adventures[adv].behaviourRules;
        			this.editAdventure.equipment = this.adventures[adv].equipment;
        			this.editAdventure.pricePerHour = this.adventures[adv].pricePerHour;
        			this.editAdventure.cancellingPrecentage = this.adventures[adv].cancellingPrecentage;
        			this.editAdventure.adventureId = id;
        		}
        	}
        },
        
         imageAdded(e){
            var files = e.target.files;
			PreviewImageEdit();
			if(!files.length)
				return;
			
				this.createImage(files[0]);
        },
        createImage(file){
			var image = new Image();
            var reader = new FileReader();
			var vm = this;

			reader.onload = (e) =>{
				this.editAdventure.photo = e.target.result;

			};
			reader.readAsDataURL(file);
        },
        
        imageAddedNew(e){
            var files = e.target.files;
			PreviewImage();
			if(!files.length)
				return;
			
				this.createImageNew(files[0]);
        },
        createImageNew(file){
			var image = new Image();
            var reader = new FileReader();
			var vm = this;

			reader.onload = (e) =>{
				this.newAdventure.photo = e.target.result;

			};
			reader.readAsDataURL(file);
        },
        
        editSelectedAdventure() {
        	if (this.checkEditAdventure()){
        	console.log(this.editAdventure)
        		axios
	               .post('/fishingAdventures/editAdventure', this.editAdventure)
	               .then(response=>{
	                  this.adventures = response.data
	               })
        	}
        	else {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke. Proverite da li su validni maksimalni broj osoba (ceo broj > 0) i cena po satu (broj > 49)!'})
        	}
        },
    },
    
    mounted(){
      this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')

        instructorsId = this.activeUser.id 
        axios
        .get('/appUser/requestExists/' + instructorsId)
        .then(response=>{
            //window.location.reload()
            this.sendCheck = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            Swal.fire({icon: 'error', title: 'Greška', text: 'Podaci su loše uneti.'})
            window.location.reload()

        })
        axios
        	.get('/fishingAdventures/getFishingInstructorsAdventures/' + instructorsId)
	        .then(response => {
	        this.adventures = response.data
	        console.log(this.adventures)
	        });
    },

});

   function PreviewImage() {
        var oFReader = new FileReader();
        oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);

        oFReader.onload = function (oFREvent) {
            document.getElementById("uploadPreview").src = oFREvent.target.result;
        };
    };

    function PreviewImageEdit() {
        var oFReader = new FileReader();
        oFReader.readAsDataURL(document.getElementById("uploadImageEdit").files[0]);

        oFReader.onload = function (oFREvent) {
            document.getElementById("uploadPreviewEdit").src = oFREvent.target.result;
        };
    };
	
	function _arrayBufferToBase64( buffer ) {
	    var binary = '';
	    var bytes = new Uint8Array( buffer );
	    var len = bytes.byteLength;
	    for (var i = 0; i < len; i++) {
	        binary += String.fromCharCode( bytes[ i ] );
	    }
    return window.btoa( binary );
	}

function _base64ArrayBuffer(arrayBuffer) {
  var base64    = ''
  var encodings = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'

  var bytes         = new Uint8Array(arrayBuffer)
  var byteLength    = bytes.byteLength
  var byteRemainder = byteLength % 3
  var mainLength    = byteLength - byteRemainder

  var a, b, c, d
  var chunk

  // Main loop deals with bytes in chunks of 3
  for (var i = 0; i < mainLength; i = i + 3) {
    // Combine the three bytes into a single integer
    chunk = (bytes[i] << 16) | (bytes[i + 1] << 8) | bytes[i + 2]

    // Use bitmasks to extract 6-bit segments from the triplet
    a = (chunk & 16515072) >> 18 // 16515072 = (2^6 - 1) << 18
    b = (chunk & 258048)   >> 12 // 258048   = (2^6 - 1) << 12
    c = (chunk & 4032)     >>  6 // 4032     = (2^6 - 1) << 6
    d = chunk & 63               // 63       = 2^6 - 1

    // Convert the raw binary segments to the appropriate ASCII encoding
    base64 += encodings[a] + encodings[b] + encodings[c] + encodings[d]
  }

  // Deal with the remaining bytes and padding
  if (byteRemainder == 1) {
    chunk = bytes[mainLength]

    a = (chunk & 252) >> 2 // 252 = (2^6 - 1) << 2

    // Set the 4 least significant bits to zero
    b = (chunk & 3)   << 4 // 3   = 2^2 - 1

    base64 += encodings[a] + encodings[b] + '=='
  } else if (byteRemainder == 2) {
    chunk = (bytes[mainLength] << 8) | bytes[mainLength + 1]

    a = (chunk & 64512) >> 10 // 64512 = (2^6 - 1) << 10
    b = (chunk & 1008)  >>  4 // 1008  = (2^6 - 1) << 4

    // Set the 2 least significant bits to zero
    c = (chunk & 15)    <<  2 // 15    = 2^4 - 1

    base64 += encodings[a] + encodings[b] + encodings[c] + '='
  }
  console.log(base64)
  return base64
}
