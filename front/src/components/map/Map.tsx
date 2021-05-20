import React from "react";

import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";

import styles from "./Map.module.css";

/*import L from "leaflet";

import icon from "leaflet/dist/images/marker-icon.png";
import iconShadow from "leaflet/dist/images/marker-shadow.png";

const DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
});

L.Marker.prototype.options.icon = DefaultIcon;*/

const Map: React.FC = () => {
  const name = "MAP";

  return (
    <div className={styles.map}>
      <MapContainer
        center={[50, 10]}
        zoom={0}
        attributionControl={true}
        zoomControl={true}
        doubleClickZoom={true}
        scrollWheelZoom={false}
        dragging={true}
        easeLinearity={0.35}
      >
        <TileLayer url="http://{s}.tile.osm.org/{z}/{x}/{y}.png" />
        <Marker position={[50, 10]}>
          <Popup>Popup for any custom information.</Popup>
        </Marker>
      </MapContainer>
    </div>
  );
};

export default Map;
