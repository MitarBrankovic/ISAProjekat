Vue.component("ProfileBoat", {
    data: function() {
        return {
            boat : "",
        }
    },
    template : ` 
    <div>
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

    </p>
    <table class="table">
        <thead>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td></td>
        </thead>
        <tbody>
            <tr>
            <td>12.12.2020.</td>
            <td>5 dana</td>
            <td>4 osobe</td>
            <td>Pet friendly</td>
            <td>500 e</td>
            <td><button type="button" class="btn btn-success">Zakazi</button> </td>
            </tr>
        </tbody>
    </table>

</div> 
    	`
    	,
    methods: {
        
    },
    mounted(){
        axios
            	.get("boats/getSelectedBoat/" + this.$route.query.id)
	            .then(response => (this.boat = response.data));
    },

});