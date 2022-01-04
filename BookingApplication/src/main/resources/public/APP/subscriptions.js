Vue.component("Subscriptions", {
    data: function() {
        return {
            activeUser:"",
            cottages:[],
            boats:[],
            adventures:[],
        }
    },
    template:`  
        <div style="margin-top: 30px;">

            <h2  class="flex title-div bigtitle">Pretplacene vikendice</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv vikendice</td>
                    <td>Adresa</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="subscription in cottages">
                    <td><a class="linkSubscription" v-on:click="showCottageInformation(subscription.cottage.id)">{{subscription.cottage.name}}</a></td>
                    <td>{{subscription.cottage.address}}</td>
                    <td>{{subscription.cottage.rating}}</td>
                    <td>{{subscription.cottage.cottageOwner.name}} {{subscription.cottage.cottageOwner.surname}}</td>
                    <td>{{subscription.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>


            <h2 class="flex title-div bigtitle" style="margin-top: 50px;">Pretplaceni brodovi</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv broda</td>
                    <td>Adresa</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="subscription in boats">
                    <td><a class="linkSubscription" v-on:click="showBoatInformation(subscription.boat.id)">{{subscription.boat.name}}</a></td>
                    <td>{{subscription.boat.address}}</td>
                    <td>{{subscription.boat.rating}}</td>
                    <td>{{subscription.boat.shipOwner.name}} {{subscription.boat.shipOwner.surname}}</td>
                    <td>{{subscription.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>

            <h2 class="flex title-div bigtitle" style="margin-top: 50px;">Pretplacene avanture</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv avanture</td>
                    <td>Adresa</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="subscription in adventures">
                    <td><a class="linkSubscription" v-on:click="showAdventureInformation(subscription.fishingAdventure.id)">{{subscription.fishingAdventure.name}}</a></td>
                    <td>{{subscription.fishingAdventure.address}}</td>
                    <td>{{subscription.fishingAdventure.rating}}</td>
                    <td>{{subscription.fishingAdventure.fishingInstructor.name}} {{subscription.fishingAdventure.fishingInstructor.surname}}</td>
                    <td>{{subscription.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
        this.$router.push('/')
        
        axios.all([axios.get('/subscribe//getAllSubscibedCottagesByClient/' + this.activeUser.id),
        axios.get('/subscribe//getAllSubscibedBoatsByClient/' + this.activeUser.id),
        axios.get('/subscribe//getAllSubscibedAdventuresByClient/' + this.activeUser.id)])
        .then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
       }))
	},
    methods:{
        showCottageInformation(id){
			this.$router.push("/profileCottage?id=" + id)
		},
        showBoatInformation(id){
			this.$router.push("/profileBoat?id=" + id)
		},
        showAdventureInformation(id){
			this.$router.push("/selectedFishingAdventure?id=" + id)
		}

    }
});
