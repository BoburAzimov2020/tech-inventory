import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICameraType } from 'app/shared/model/camera-type.model';
import { getEntities as getCameraTypes } from 'app/entities/camera-type/camera-type.reducer';
import { ICameraBrand } from 'app/shared/model/camera-brand.model';
import { getEntities as getCameraBrands } from 'app/entities/camera-brand/camera-brand.reducer';
import { ICamera } from 'app/shared/model/camera.model';
import { CameraStatus } from 'app/shared/model/enumerations/camera-status.model';
import { getEntity, updateEntity, createEntity, reset } from './camera.reducer';

export const CameraUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cameraTypes = useAppSelector(state => state.cameraType.entities);
  const cameraBrands = useAppSelector(state => state.cameraBrand.entities);
  const cameraEntity = useAppSelector(state => state.camera.entity);
  const loading = useAppSelector(state => state.camera.loading);
  const updating = useAppSelector(state => state.camera.updating);
  const updateSuccess = useAppSelector(state => state.camera.updateSuccess);
  const cameraStatusValues = Object.keys(CameraStatus);

  const handleClose = () => {
    navigate('/camera' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCameraTypes({}));
    dispatch(getCameraBrands({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...cameraEntity,
      ...values,
      cameraType: cameraTypes.find(it => it.id.toString() === values.cameraType.toString()),
      cameraBrand: cameraBrands.find(it => it.id.toString() === values.cameraBrand.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'OFFLINE',
          ...cameraEntity,
          cameraType: cameraEntity?.cameraType?.id,
          cameraBrand: cameraEntity?.cameraBrand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.camera.home.createOrEditLabel" data-cy="CameraCreateUpdateHeading">
            Создать или отредактировать Camera
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="camera-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="camera-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Это поле обязательно к заполнению.' },
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="nameLabel">Nomi &#39;Camera1&#39;</UncontrolledTooltip>
              <ValidatedField
                label="Model"
                id="camera-model"
                name="model"
                data-cy="model"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="modelLabel">Model.</UncontrolledTooltip>
              <ValidatedField
                label="Serial Number"
                id="camera-serialNumber"
                name="serialNumber"
                data-cy="serialNumber"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="serialNumberLabel">Seriya raqami.</UncontrolledTooltip>
              <ValidatedField
                label="Ip"
                id="camera-ip"
                name="ip"
                data-cy="ip"
                type="text"
                validate={{
                  required: { value: true, message: 'Это поле обязательно к заполнению.' },
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="ipLabel">IP</UncontrolledTooltip>
              <ValidatedField label="Status" id="camera-status" name="status" data-cy="status" type="select">
                {cameraStatusValues.map(cameraStatus => (
                  <option value={cameraStatus} key={cameraStatus}>
                    {cameraStatus}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="statusLabel">Status</UncontrolledTooltip>
              <ValidatedField
                label="Info"
                id="camera-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="camera-cameraType" name="cameraType" data-cy="cameraType" label="Camera Type" type="select">
                <option value="" key="0" />
                {cameraTypes
                  ? cameraTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="camera-cameraBrand" name="cameraBrand" data-cy="cameraBrand" label="Camera Brand" type="select">
                <option value="" key="0" />
                {cameraBrands
                  ? cameraBrands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/camera" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Назад</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Сохранить
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CameraUpdate;
