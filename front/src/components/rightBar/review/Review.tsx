import React from "react";

import styles from "./Review.module.css";

interface IReview {
  name: string;
}

export type { IReview };

const Review: React.FC<IReview> = ({ name }: IReview) => {
  const description = "Description";
  const comment = "Comment";

  const active = "active";

  const assignTabName = (currentTab: string) => {
    return `tablinks ${activeTab === currentTab ? active : ""}`;
  };

  const [activeTab, setActiveTab] = React.useState<string | undefined>(
    undefined,
  );

  const handleTabClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    setActiveTab((e.target as any).id);
  };

  let content;

  switch (activeTab) {
    case description: {
      content = `Description of ${name}`;
      break;
    }
    case comment: {
      content = `Comment of ${name}`;
      break;
    }
    default: {
      break;
    }
  }

  return (
    <div className={styles.review}>
      <div className={styles.tabs}>
        <button
          className={assignTabName(description)}
          onClick={handleTabClick}
          id={description}
        >
          {description}
        </button>
        <button
          className={assignTabName(comment)}
          onClick={handleTabClick}
          id={comment}
        >
          {comment}
        </button>
      </div>
      <div className={styles.content}>{content}</div>
    </div>
  );
};

export default Review;
