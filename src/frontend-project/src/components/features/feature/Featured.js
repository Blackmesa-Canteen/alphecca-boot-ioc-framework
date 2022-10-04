import React from "react";
import {
  FeaturedContainer,
  FeaturedImg,
  FeaturedItem,
  FeatureTitles,
} from "./FeaturedElements";

const Featured = () => {
  return (
    <FeaturedContainer>
      <FeaturedItem>
        <FeaturedImg
          src="https://www.gostudy.com.au/wp-content/uploads/2018/10/MEL_header_2-1.jpg"
          alt=""
        />
        <FeatureTitles>
          <h1>Melbourne</h1>
        </FeatureTitles>
      </FeaturedItem>

      <FeaturedItem>
        <FeaturedImg
          src="https://manofmany.com/wp-content/uploads/2020/10/Sydney-City.jpg"
          alt=""
        />
        <FeatureTitles>
          <h1>Sydney</h1>
        </FeatureTitles>
      </FeaturedItem>

      <FeaturedItem>
        <FeaturedImg
          src="https://upload.wikimedia.org/wikipedia/commons/c/ca/Skyline_of_Brisbane_from_Kangaroo_Point_Cliffs_Park%2C_Nov_2020%2C_05.jpg"
          alt=""
        />
        <FeatureTitles>
          <h1>Brisbane</h1>
        </FeatureTitles>
      </FeaturedItem>
    </FeaturedContainer>
  );
};

export default Featured;
