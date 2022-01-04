Vue.component("SearchBoats",{
    data:function(){
        return{
            search:{
                nameAsc:false,
                nameDesc:false,
                addressAsc:false,
                addressDesc:false,
                rateAsc:false,
                rateDesc:false,
                name:"",
                address:"",

            }
        }
    },
    template:`
        <div>
            <h3 id="search3">Pretraga brodova</h3>
            <input type="text" v-model="search.name" placeholder="naziv"/>
            <input type="text" v-model="search.address" placeholder="adresa"/>
            <button type="button" v-on:click="searchFun()" class="btn btn-sm btn-success bi bi-search">Pretrazi</button>
            <button type="button" v-on:click="cancelSearch()" class="btn btn-sm btn-outline-danger bi bi-x">Otkazi</button>
            <br>
        
            <div style="margin-top:5px">
                <button class="bi bi-arrow-up btn btn-secondary" type="button" v-on:click="nameAscFun()">Naziv</button>
                <button class="bi bi-arrow-down btn btn-secondary" type="button" v-on:click="nameDescFun()">Naziv</button>
                <button class="bi bi-arrow-up btn btn-secondary" type="button" v-on:click="addressAscFun()">Adresa</button>
                <button class="bi bi-arrow-down btn btn-secondary" type="button" v-on:click="addressDescFun()">Adresa</button>
                <button class="bi bi-arrow-up btn btn-secondary" type="button" v-on:click="rateAscFun()">Ocena</button>
                <button class="bi bi-arrow-down btn btn-secondary" type="button" v-on:click="rateDescFun()">Ocena</button>
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
            this.search.rateAsc = false
            this.search.rateDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        nameDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = true
            this.search.addressAsc = false
            this.search.addressDesc = false
            this.search.rateAsc = false
            this.search.rateDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = true
            this.search.addressDesc = false
            this.search.rateAsc = false
            this.search.rateDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = true
            this.search.rateAsc = false
            this.search.rateDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        rateAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false
            this.search.rateAsc = true
            this.search.rateDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        rateDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false
            this.search.rateAsc = false
            this.search.rateDesc = true
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        cancelSearch: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false
            this.search.rateAsc = false
            this.search.rateDesc = false      
            this.search.name="",
            this.search.address="",
            console.log("Klik!")
            this.$emit('clicked', this.search)
        }
    }
})