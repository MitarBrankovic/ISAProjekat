Vue.component("SearchAdvAppointments",{
    data:function(){
        return{
            activeUser:"",
            search:{
                nameAsc:false,
                nameDesc:false,
                dateAsc:false,
                dateDesc:false,
                durationAsc:false,
                durationDesc:false,
                priceAsc:false,
                priceDesc:false,
                name:"",
                owner:"",
                activeUserId:""

            }
        }
    },
    template:`
        <div>
            <h3 id="search3">Pretraga termina avantura</h3>
            <input type="text" v-model="search.name" placeholder="name"/>
            <input type="text" v-model="search.owner" placeholder="address"/>
            <button type="button" v-on:click="searchFun()" class="btn btn-sm btn-primary bi bi-search">Search</button>
            <button type="button" v-on:click="cancelSearch()" class="btn btn-sm btn-outline-danger bi bi-x">Cancel search</button>
            <br>
        
            <div>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="dateAscFun()">Date ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="dateDescFun()">Date descending</button>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="priceAscFun()">Price ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="priceDescFun()">Price descending</button>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="durationAscFun()">Duration ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="durationDescFun()">Duration descending</button>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="nameAscFun()">Name ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="nameDescFun()">Name descending</button>
            </div>
        
        
        
        
        </div>                
    `,
    methods:{
        searchFun:function(){
            this.search.activeUserId = this.activeUser.id
            this.$emit('clicked',this.search)
        },
        nameAscFun: function(){
            this.search.nameAsc = true
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        nameDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = true
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        dateAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = true
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        dateDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = true
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        durationAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = true
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        durationDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = true
            this.search.priceAsc = false
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        priceAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = true
            this.search.priceDesc = false
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        priceDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = true
            this.search.activeUserId = this.activeUser.id
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        cancelSearch: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.dateAsc = false
            this.search.dateDesc = false
            this.search.durationAsc = false
            this.search.durationDesc = false
            this.search.priceAsc = false
            this.search.priceDesc = false   
            this.search.activeUserId = this.activeUser.id  
            this.search.name="",
            this.search.owner="",
            console.log("Klik!")
            this.$emit('clicked', this.search)
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
	}
})