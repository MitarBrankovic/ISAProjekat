Vue.component("SearchInstructorsAdventures",{
    data:function(){
        return{
            search:{
                nameAsc:false,
                nameDesc:false,
                addressAsc:false,
                addressDesc:false,
                name:"",
                address:"",
                instructorsId: "",
            }
        }
    },
    template:`
      <div>
          <div class="row">
          	  <div class="col col-sm-1">
          	  <h4>Pretraga</h4>
	      	  </div>
	          <div class="col col-sm-2">
	          	<input type="text" class="form-control" v-model="search.name" placeholder="Unesite ime..."/>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<input type="text" class="form-control" v-model="search.address" placeholder="Unesite adresu..."/>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button type="button" v-on:click="searchFun()" class="btn btn-primary bi bi-search">Pretraži</button>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button type="button" v-on:click="cancelSearch()" class="btn  btn-outline-danger bi bi-x">Otkaži pretragu/filter</button>
	      	  </div>
	      	  <div class="col col-sm-3">
	      	  </div>
	      </div>
	      <div class="row">
	      	  <div class="col col-sm-1">
	      	  <h4>Filteri</h4>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button class="bi bi-arrow-up btn btn-secondary" type="button" v-on:click="nameAscFun()">Ime - rastuće</button>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button class="bi bi-arrow-down btn btn-secondary" type="button" v-on:click="nameDescFun()">Ime - opadajuće</button>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button class="bi bi-arrow-up btn btn-secondary" type="button" v-on:click="addressAscFun()">Adresa - rastuće</button>
	      	  </div>
	      	  <div class="col col-sm-2">
	          	<button class="bi bi-arrow-down btn btn-secondary" type="button" v-on:click="addressDescFun()">Adresa - opadajuće</button>
	      	  </div>
	      	  <div class="col col-sm-3">
	      	  </div>
       	</div>   
    </div>             
    `,
    methods:{
        searchFun:function(){
            this.$emit('clicked',this.search)
        },
        nameAscFun: function(){
            this.search.nameAsc = true
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        nameDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = true
            this.search.addressAsc = false
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = true
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = true
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        cancelSearch: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false      
            this.search.name="",
            this.search.address="",
            console.log("Klik!")
            this.$emit('clicked', this.search)
        }
    },
    mounted(){
    	let user = JSON.parse(localStorage.getItem('activeUser'))
    	this.search.instructorsId = user.id;
    }
})