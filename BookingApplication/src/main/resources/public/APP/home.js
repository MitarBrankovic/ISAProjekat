Vue.component("Home", {
    data: function() {
        return {
            cottagesButton:true,
            boatsButton:false,
            adventuresButton:false,
            cottages:[],
            boats:[],
            adventures:[],
            activeUser: null,
            adventureIdRemove: ""
        }
    },

template: ` 
	<div>
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="height:400px; width:100%;">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner" style="height: 100%;">
                <div class="carousel-item active">
                    <img src="../images/carousel1.jpg" style="height:100%; max-height:500px" class="d-block h-100 w-100" alt="...">
                    <div class="carousel-content" style="width: 25%;">
                        <h2 class="centerIt">Welcome to</h2>
                        <h1 class="centerIt">Booking App</h1>
                        <div style="margin-top: 30px;" class="nav nav-tabs centerIt">
                            <button class="button-tab nav-item" type="button" v-on:click="cottagesFun()">Cottages</button>
                            <button style="margin-right: 8px; margin-left: 8px;" class="button-tab nav-item" type="button" v-on:click="boatsFun()">Boats</button>
                            <button class="button-tab nav-item" type="button" v-on:click="adventuresFun()">Adventures</button>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="../images/carousel2.jpg" style="height:100%; max-height:500px" class="d-block w-100" alt="...">
                    <div class="carousel-content" style="width: 25%;">
                        <h2 class="centerIt">Welcome to</h2>
                        <h1 class="centerIt">Booking App</h1>
                        <div style="margin-top: 30px;" class="nav nav-tabs centerIt">
                            <button class="button-tab nav-item" type="button" v-on:click="cottagesFun()">Cottages</button>
                            <button style="margin-right: 8px; margin-left: 8px;" class="button-tab nav-item" type="button" v-on:click="boatsFun()">Boats</button>
                            <button class="button-tab nav-item" type="button" v-on:click="adventuresFun()">Adventures</button>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="../images/carousel3.jpg" style="height:100%; max-height:500px" class="d-block w-100" alt="...">
                    <div class="carousel-content" style="width: 25%;">
                        <h2 class="centerIt">Welcome to</h2>
                        <h1 class="centerIt">Booking App</h1>
                        <div style="margin-top: 30px;" class="nav nav-tabs centerIt">
                            <button class="button-tab nav-item" type="button" v-on:click="cottagesFun()">Cottages</button>
                            <button style="margin-right: 8px; margin-left: 8px;" class="button-tab nav-item" type="button" v-on:click="boatsFun()">Boats</button>
                            <button class="button-tab nav-item" type="button" v-on:click="adventuresFun()">Adventures</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>

        <div v-if="cottagesButton">
            <SearchCottages id="search" @clicked="searchCottages"></SearchCottages>

            <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <div class="col"  v-for = "c in cottages">
                        <div class="card" style="width: 93%">
                            <img src="../images/cottage.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{c.name}}</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><b>Opis:</b> {{c.description}}</li>
                                <li class="list-group-item"><b>Adresa:</b> {{c.address}}</li>
                                <li class="list-group-item"><b>Vlasnik:</b> {{c.cottageOwner.name}} {{c.cottageOwner.surname}}</li>
                                <li class="list-group-item"><b>Cena:</b> {{c.pricePerHour}}din/h</li>
                                <li class="list-group-item"><b>Ocena:</b> {{c.rating}}</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" v-on:click="showCottageInformation(c.id)" class="btn btn-secondary">Info</button>
                                <button  style="margin-left: 16%;" v-if="activeUser != null && (activeUser.role == 'admin' || activeUser.role == 'cottage_owner')" type="button" data-bs-toggle="modal" data-bs-target="#areYouSureCottage" v-on:click="prepareAdventureToRemove(c.id)" class="btn btn-danger">Obrisi</button>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
        </div>


        <div v-else-if="boatsButton">
            <SearchBoats id="search" @clicked="searchBoats"></SearchBoats>

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
                                <button style="margin-left: 16%;" v-if="activeUser != null && (activeUser.role == 'admin' || activeUser.role == 'ship_owner')" type="button" data-bs-toggle="modal" data-bs-target="#areYouSureBoat" v-on:click="prepareAdventureToRemove(b.id)" class="btn btn-danger">Obrisi</button>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
        </div>

        <div v-else-if="adventuresButton">
            <SearchAdventures id="search" @clicked="searchAdventures"></SearchAdventures>

            <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <div class="col"  v-for = "a in adventures">
                        <div class="card" style="width: 93%">
                            <!--<img :src="a.photo" width="300" height="220" class="card-img-top" alt="...">-->
                            <img src="../images/fishing.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{a.name}}</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><b>Opis:</b> {{a.description}}</li>
                                <li class="list-group-item"><b>Adresa:</b> {{a.city}} {{a.address}}</li>
                                <li class="list-group-item"><b>Instruktor:</b> {{a.fishingInstructor.name}} {{a.fishingInstructor.surname}}</li>
                                <li class="list-group-item"><b>Cena:</b> {{a.pricePerHour}}din/h</li>
                                <li class="list-group-item"><b>Ocena:</b> {{a.rating}}</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" v-on:click="showAdventureInformation(a.id)" class="btn btn-secondary">Info</button>
                                <button style="margin-left: 16%;" v-if="activeUser != null && (activeUser.role == 'admin' || (activeUser.role == 'fishing_instructor' && activeUser.id == a.fishingInstructor.id))" type="button" data-bs-toggle="modal" data-bs-target="#areYouSureFishing" v-on:click="prepareAdventureToRemove(a.id)" class="btn btn-danger">Obriši</button>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
        </div>

	<div class="modal fade" id="areYouSureFishing" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
	
	<div class="modal fade" id="areYouSureBoat" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
	        <button type="button" class="btn btn-danger" v-on:click="removeBoat()" data-bs-dismiss="modal" >Da</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="areYouSureCottage" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
	        <button type="button" class="btn btn-danger" v-on:click="removeCottage()" data-bs-dismiss="modal" >Da</button>
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
        searchCottages:function(search){
            console.log(search)
            axios
            .post('/cottages/searchCottages',search)
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
		removeAdventure() {
    		axios
               .post('/fishingAdventures/checkAdventureRemoval/' + this.adventureIdRemove)
               .then(response=>{
                  if (response.data) {
                  	 axios
	                 .post('/fishingAdventures/removeAdventure/' + this.adventureIdRemove)
	                 .then(response => { window.location.reload()})
                  }
                  else {
                  	Swal.fire({icon: 'error', title: 'Ne možete obrisati ovu avanturu, jer postoje zakazani termini za nju !'})
                  }
               })
    	},
    	prepareAdventureToRemove(id){
        	this.adventureIdRemove = id;		
        },
        removeBoat() {
    		axios
               .post('/boats/checkBoatRemoval/' + this.adventureIdRemove)
               .then(response=>{
                  if (response.data) {
                  	 axios
	                 .post('/boats/removeBoat/' + this.adventureIdRemove)
	                 .then(response => { window.location.reload()})
                  }
                  else {
                  	Swal.fire({icon: 'error', title: 'Ne možete obrisati ovaj brod, jer postoje zakazani termini za njega !'})
                  }
               })
    	},
    	removeCottage() {
    		axios
               .post('/cottages/checkCottageRemoval/' + this.adventureIdRemove)
               .then(response=>{
                  if (response.data) {
                  	 axios
	                 .post('/cottages/removeCottage/' + this.adventureIdRemove)
	                 .then(response => { window.location.reload()})
                  }
                  else {
                  	Swal.fire({icon: 'error', title: 'Ne možete obrisati ovu vikendicu, jer postoje zakazani termini za nju !'})
                  }
               })
    	},


    },
    
    mounted() {
    	this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios.all([axios.get('/cottages/getAllCottages'), axios.get('/boats/getAllBoats'),
        axios.get('/fishingAdventures/getAllAdventures'),
        axios.get('/reports/deleteAllReportsFirstInMonth')]).then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
       }))
    }
});