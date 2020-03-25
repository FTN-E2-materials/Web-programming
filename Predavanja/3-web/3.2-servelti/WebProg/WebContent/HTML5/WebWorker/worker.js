console.log("Prva naredba u workeru je slanje postMessage izvan onMessage handlera.");
self.postMessage("Šaljem poruku u glavnu nit, izvan onmessage handlera.");

self.onmessage = function (oEvent) {
  console.log("Worker primio: " + oEvent.data);
  self.postMessage("Hi " + oEvent.data);
};