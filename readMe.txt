
This is a web app for a railways.

    Possible ways to interact with this app are listed below (specifically for the test cases provided)
    One key assumption made in the logic (apart form the ones mentioned in the question),
        if the distance travelled is 0 we don't consider it as a valid trip.

    #http://localhost:8080/admin/add-route/AC12

    http://localhost:8080/admin/add-routes/AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7

    http://localhost:8080/query/shortest-distance-for/A-B-C
    http://localhost:8080/query/shortest-distance-for/A-D
    http://localhost:8080/query/shortest-distance-for/A-D-C
    http://localhost:8080/query/shortest-distance-for/A-E-B-C-D
    http://localhost:8080/query/shortest-distance-for/A-E-D

    http://localhost:8080/query/count-trips-with-max-hop?from-station=C&to-station=C&max-hop=3

    http://localhost:8080/query/count-trips-with-exact-hop?from-station=A&to-station=C&exact-hop=4

    http://localhost:8080/query/shortest-route-distance-between?from-station=A&to-station=C
    http://localhost:8080/query/shortest-route-distance-between?from-station=B&to-station=B

    http://localhost:8080/query/count-trips-with-max-distance?from-station=C&to-station=C&max-distance=30