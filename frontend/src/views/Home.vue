<template>
    <div class="main-container">  
        <h1>DADOS TRANSFERÊNCIA</h1>
        <Transferencia />
    </div>
</template>

<script>
    import Transferencia from '@/components/Transferencia.vue';

    export default {
        name: "Home",
        components: {
            Transferencia
        }
    }
</script>