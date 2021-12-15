Vue.component("ProfileCottage", {
    data: function() {
        return {
            cottage : "",
        }
    },
    template : ` 
    <div>
    <h1>{{this.cottage.name}}</h1>address
    <p>Adresa: {{this.cottage.address}}
    <br> Opis :{{this.cottage.description}}
    <br> Broj soba: {{this.cottage.roomsNum}}
    <br> Broj kreveta po sobi : {{this.cottage.bedsNum}}
    <br> Pravila : {{this.cottage.rules}}
    <br> Cenovnik i dodatne usluge:
    <br> {{this.cottage.priceList}}
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
            <td>Wifi</td>
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
            	.get("cottages/getSelectedCottage/" + this.$route.query.id)
	            .then(response => (this.cottage = response.data));
    },

});