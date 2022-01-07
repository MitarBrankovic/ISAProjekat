Vue.component("LoyaltyProgramAdmin", {
    data: function() {
        return {
            activeUser:"",
            loyalty: "",
        }
    },
    template :`
    <div>
 
    <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
    <div class="col-sm-6">
			<h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c">Loyalty program - definisanje</h2>
        </div>
        <div class="col-sm-3">
        	<button type="button" style="margin-top: 2%; margin-left: 7%;" v-on:click="enableLoyaltyEdit()" class="btn btn-secondary btn-lg">Promeni podatke</button>
        </div>
        
        <div class="col-sm-3">
			<button type="button" style="margin-top: 2%; margin-left: 7%;" v-on:click="editLoyalty()" class="btn btn-success btn-lg">Sačuvaj izmene</button>
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Bronzani korisnik - broj potrebnih bodova</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="bronze_client" min="10" step="10" v-model="loyalty.bronzePoints" disabled class="form-control"  placeholder="Unesite broj potrebnih poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Srebrni korisnik - broj potrebnih bodova</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="silver_client" min="10" step="10" v-model="loyalty.silverPoints" disabled class="form-control"  placeholder="Unesite broj potrebnih poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Zlatni korisnik - broj potrebnih bodova</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="gold_client" min="10" step="10" v-model="loyalty.goldPoints" disabled class="form-control"  placeholder="Unesite broj potrebnih poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Bronzani korisnik - broj poena za svaku rezervaciju</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="bronze_points" min="10" step="10" disabled v-model="loyalty.bronzeClient" class="form-control"  placeholder="Unesite broj poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Srebrni korisnik - broj poena za svaku rezervaciju</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="silver_points" min="10" step="10" disabled v-model="loyalty.silverClient" class="form-control"  placeholder="Unesite broj poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Zlatni korisnik - broj poena za svaku rezervaciju</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="gold_points" min="10" step="10" v-model="loyalty.goldClient" disabled class="form-control"  placeholder="Unesite broj poena...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Bronzani klijent - popust na rezervacije (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="bronze_discount" min="0" max="99" step="1" disabled v-model="loyalty.bronzeDiscount" class="form-control"  placeholder="Unesite popust...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Srebrni klijent - popust na rezervacije (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="silver_discount" min="0" max="99" step="1" disabled v-model="loyalty.silverDiscount" class="form-control"  placeholder="Unesite popust...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Zlatni klijent - popust na rezervacije (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="gold_discount" min="0" max="99" step="1" disabled v-model="loyalty.goldDiscount" class="form-control"  placeholder="Unesite popust...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Bronzani vlasnik/instruktor - dodatni profit na rezervaciju (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="bronze_profit" min="0" max="99" step="1" disabled class="form-control" v-model="loyalty.bronzeProfit" placeholder="Unesite profit...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Srebrni vlasnik/instruktor - dodatni profit na rezervaciju (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="silver_profit" min="0" max="99" step="1" disabled class="form-control" v-model="loyalty.silverProfit" placeholder="Unesite profit...">
        </div>
    </div>
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6">
        	<span class="input-group-text" style="color:#5cb85c">Zlatni vlasnik/instruktor - dodatni profit na rezervaciju (%)</span>
        </div>
        <div class="col-sm-6">
			<input type="number" id="gold_profit" min="0" max="99" step="1" disabled class="form-control" v-model="loyalty.goldProfit" placeholder="Unesite profit...">
        </div>
    </div>
	</div>
    
</div>	  
    	`
    	,
    methods: {
    	editLoyalty() {
    	if (this.checkLoyaltyValidity())
	    	axios
		        .post('/loyalty/editLoyalty/', this.loyalty)
		        .then(response=>{
		            this.loyalty = response.data
		            this.disableLoyaltyEdit();
		            Swal.fire({ icon: 'success', title: 'Uspešno ste promenili loyalty program !', showConfirmButton: false, timer: 1000 })
		        })
		        .catch(error=>{
		            console.log("Greska.")	
		            alert("Podaci su lose uneti.")
		            window.location.reload()	
		        })
		else
			Swal.fire({
                    icon: 'error',
                    title: 'Greška',
                    text: 'Niste uneli validne vrednosti !',
                  })
    	},
    	
    	checkLoyaltyValidity() {
    		if (this.loyalty.bronzePoints >= 0 && this.loyalty.silverPoints >= 0 && this.loyalty.goldPoints >= 0 && 
	    		this.loyalty.bronzeClient >= 0 && this.loyalty.silverClient >= 0 && this.loyalty.goldClient >= 0 && 
	    		this.loyalty.goldDiscount >= 0 && this.loyalty.goldDiscount < 100 && this.loyalty.silverDiscount >= 0 && 
	    		this.loyalty.silverDiscount < 100 && this.loyalty.bronzeDiscount >= 0 && this.loyalty.bronzeDiscount < 100 && 
	    		this.loyalty.goldProfit >= 0 && this.loyalty.goldProfit < 100 && this.loyalty.silverProfit >= 0 && 
	    		this.loyalty.silverProfit < 100 && this.loyalty.bronzeProfit >= 0 && this.loyalty.bronzeProfit < 100)
    		return true;
    		return false;
    	},
    	
    	enableLoyaltyEdit() {
    		document.getElementById("bronze_client").disabled = false 
			document.getElementById("bronze_discount").disabled = false 
			document.getElementById("bronze_profit").disabled = false 
			document.getElementById("bronze_points").disabled = false 
			document.getElementById("silver_client").disabled = false 
			document.getElementById("silver_discount").disabled = false 
			document.getElementById("silver_profit").disabled = false 
			document.getElementById("silver_points").disabled = false 
			document.getElementById("gold_client").disabled = false 
			document.getElementById("gold_discount").disabled = false 
			document.getElementById("gold_profit").disabled = false 
			document.getElementById("gold_points").disabled = false 
    	}, 
    	
    	disableLoyaltyEdit() {
    		document.getElementById("bronze_client").disabled = true 
			document.getElementById("bronze_discount").disabled = true 
			document.getElementById("bronze_profit").disabled = true 
			document.getElementById("bronze_points").disabled = true 
			document.getElementById("silver_client").disabled = true 
			document.getElementById("silver_discount").disabled = true 
			document.getElementById("silver_profit").disabled = true 
			document.getElementById("silver_points").disabled = true 
			document.getElementById("gold_client").disabled = true 
			document.getElementById("gold_discount").disabled = true 
			document.getElementById("gold_profit").disabled = true 
			document.getElementById("gold_points").disabled = true 
    	}    	
    },
    mounted(){
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
            this.$router.push('/')
        axios
        .get('/loyalty/getLoyalty')
        .then(response=>{
            this.loyalty = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
    },

});