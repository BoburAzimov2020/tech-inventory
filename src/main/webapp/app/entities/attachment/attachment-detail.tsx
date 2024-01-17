import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attachment.reducer';

export const AttachmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">Attachment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="path">Path</span>
          </dt>
          <dd>{attachmentEntity.path}</dd>
          <dt>
            <span id="originalFileName">Original File Name</span>
          </dt>
          <dd>{attachmentEntity.originalFileName}</dd>
          <dt>
            <span id="fileName">File Name</span>
          </dt>
          <dd>{attachmentEntity.fileName}</dd>
          <dt>
            <span id="contentType">Content Type</span>
          </dt>
          <dd>{attachmentEntity.contentType}</dd>
          <dt>
            <span id="fileSize">File Size</span>
          </dt>
          <dd>{attachmentEntity.fileSize}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{attachmentEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{attachmentEntity.obyekt ? attachmentEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttachmentDetail;
