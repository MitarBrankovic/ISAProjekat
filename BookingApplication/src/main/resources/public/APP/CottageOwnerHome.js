Vue.component("CottageOwnerHome", {
    data: function() {
        return {        
			activeUser:"",
            cottages:[]
        }
    },

template: ` 
	<div>
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
                                <li class="list-group-item">{{c.description}}</li>
                                <li class="list-group-item">{{c.address}}</li>
                                <li class="list-group-item">Num of rooms: {{c.roomsNum}}</li>
                                <li class="list-group-item">Num of beds: {{c.bedsNum}}</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" v-on:click="showCottageInformation(c.id)" class="btn btn-secondary">Info</button>
                                <button style="margin-left: 8%;" type="button" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" class="btn btn-danger">Obrisi</button>
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
		}

    },
    
    mounted() {
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios.all([axios.get('/cottages/getAllCottagesByOwner/' + this.activeUser.id )]).then(axios.spread((...responses) => {
           this.cottages = responses[0].data

       }))
    }
});