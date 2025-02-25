<template>
    <div id="nav">
        <router-link to="/" id="logo-url">
            <img :src="logo" :alt="alt" id="logo">
        </router-link>
        <router-link to="/">Home</router-link> |
        <router-link to="/transferencias">Transferências</router-link>
    </div>
</template>
<script>
export default {
    name: "Navbar",
    props: ["logo", "alt"]
}
</script>
<style scoped>
    #nav {
        background-color: #222;
        padding: 7px 50px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        color: #ffffff;
    }

    #nav #logo-url {
        margin: auto;
        margin-left: 0;
    }

    #logo {
        widows: 50px;
        height: 50px;
    }

    #nav a {
        color: #ffffff;;
        text-decoration: none;
        margin: 12px;
        transition: .5s;
    }

    #nav a:hover {
        color: #fff
    }
</style>
  